package com.bargainhunter.bargainhunterandroid.gps;

import android.location.Location;
import com.bargainhunter.bargainhunterandroid.models.entities.Coordinates;

/**
 * Created by vasovourka on 11/9/14.
 */
public class DistanceCalc {
    public static float calculate(MockLocations mock,Coordinates coordinates) {

        float distance=0;



        Location loc1=new Location("current location");
        loc1.setLatitude(coordinates.getLatitude());
        loc1.setLongitude(coordinates.getLongitude());

        Location loc2 = new Location("store location");

        loc2.setLatitude(mock.getLatitude());
        loc2.setLongitude(mock.getLongitude());

        distance=loc1.distanceTo(loc2);


        return distance;
    }


}
