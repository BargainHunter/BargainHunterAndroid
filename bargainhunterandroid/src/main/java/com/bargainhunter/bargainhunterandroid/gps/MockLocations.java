package com.bargainhunter.bargainhunterandroid.gps;

/**
 * Created by vasovourka on 11/9/14.
 */
public class MockLocations {
    private String fullAdress;
    private double latitude,longitude;


    public void setFullAdress(String fullAdress) {
        this.fullAdress = fullAdress;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getFullAdress() {
        return fullAdress;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }




}
