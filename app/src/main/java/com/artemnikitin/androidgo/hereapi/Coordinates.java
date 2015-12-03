package com.artemnikitin.androidgo.hereapi;

import java.io.Serializable;

public class Coordinates implements Serializable {

    private final double latitude;
    private final double longitude;

    public Coordinates(String coordinates) {
        if (coordinates.contains("||")) {
            int position = coordinates.indexOf("||");
            latitude = Double.parseDouble(coordinates.substring(0, position));
            longitude = Double.parseDouble(coordinates.substring(position + 2, coordinates.length()));
        } else {
            latitude = 0;
            longitude = 0;
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
