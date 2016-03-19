package com.artemnikitin.androidgo;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import com.artemnikitin.androidgo.hereapi.Client;
import com.artemnikitin.androidgo.hereapi.Coordinates;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        ConnectionCallbacks, OnConnectionFailedListener {

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected DeviceInfo mDeviceInfo;
    protected Client client;
    protected ImageView image;
    protected String apiId;
    protected String apiToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        image = (ImageView) findViewById(R.id.image);
        setSupportActionBar(toolbar);
        setCredentials();

        mDeviceInfo = new DeviceInfo(this.getApplicationContext());
        client = new Client(apiId, apiToken);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        com.artemnikitin.androidgo.AddressActivity.class);
                intent.putExtra("api.client", client);
                startActivityForResult(intent, 1);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Coordinates coordinates = (Coordinates) data.getSerializableExtra("coordinates");
            setImage(coordinates.getLatitude(), coordinates.getLongitude());
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation == null) {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        } else {
            if (image.getDrawable() == null) {
                setImage(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("ANDROID_GO", connectionResult.getErrorMessage());
    }

    private void setCredentials() {
        try {
            ApplicationInfo app = getPackageManager().getApplicationInfo(
                    this.getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;
            apiId = bundle.getString("appid");
            apiToken = bundle.getString("apptoken");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("ANDROID_GO", e.toString());
        }
    }

    private void setImage(double latitude, double longitude) {
        byte[] imageBytes = client.getImage(latitude, longitude, mDeviceInfo);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        image.setImageBitmap(bitmap);
    }

}
