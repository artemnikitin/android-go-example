package com.artemnikitin.androidgo;

import com.artemnikitin.androidgo.hereapi.Client;
import com.artemnikitin.androidgo.hereapi.Coordinates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddressActivity extends AppCompatActivity {

    protected EditText textField;
    protected Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        button = (Button) findViewById(R.id.get_coordinates);
        textField = (EditText) findViewById(R.id.input_text);

        Intent intent = getIntent();
        final Client client = (Client) intent.getSerializableExtra("api.client");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = textField.getText().toString();
                if (client != null) {
                    if (!address.equals("")) {
                        Coordinates coordinates = client.getCoordinates(address);
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("coordinates", coordinates);
                        setResult(Activity.RESULT_OK, returnIntent);
                    }
                } else {
                    setResult(Activity.RESULT_CANCELED, new Intent());
                }
                finish();
            }
        });
    }

}
