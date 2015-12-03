package com.artemnikitin.androidgo.hereapi;

import android.util.Log;

import com.artemnikitin.androidgo.DeviceInfo;

import java.io.Serializable;

import go.hereapi.Hereapi;

public class Client implements Serializable {

    private final String appId;
    private final String appToken;

    public Client(String appId, String appToken) {
        this.appId = appId;
        this.appToken = appToken;
    }

    public byte[] getImage(double latitude, double longitude, DeviceInfo deviceInfo) {
        return Hereapi.GetPicture(
                appId, appToken, latitude, longitude,
                deviceInfo.getHeight(), deviceInfo.getWidth(),
                deviceInfo.getDpi());
    }

    public Coordinates getCoordinates(String text) {
        String coordinates = Hereapi.GetCoordinates(appId, appToken, text);
        if (coordinates != null)
            return new Coordinates(coordinates);
         else
            return new Coordinates("0||0");
    }

}
