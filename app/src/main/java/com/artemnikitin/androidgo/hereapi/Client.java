package com.artemnikitin.androidgo.hereapi;

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
        byte[] result = Hereapi.GetPicture(
                appId, appToken, latitude, longitude,
                deviceInfo.getHeight(), deviceInfo.getWidth(),
                deviceInfo.getDpi());
        return result == null ? new byte[0]:result;
    }

    public Coordinates getCoordinates(String text) {
        String coordinates = Hereapi.GetCoordinates(appId, appToken, text);
        return coordinates == null ? new Coordinates("0||0"):new Coordinates(coordinates);
    }

}
