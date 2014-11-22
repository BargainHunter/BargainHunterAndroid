package com.bargainhunter.bargainhunterandroid.models.entities;

/**
 * Created by vasovourka on 11/8/14.
 */
public class Coordinates {
    private double latitude,longitude;

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
