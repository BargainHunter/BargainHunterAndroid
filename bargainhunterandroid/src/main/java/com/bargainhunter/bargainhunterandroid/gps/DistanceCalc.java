package com.bargainhunter.bargainhunterandroid.gps;

import android.location.Location;
import com.bargainhunter.bargainhunterandroid.models.Coordinates;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;

/**
 * Created by vasovourka on 11/9/14.
 */
public class DistanceCalc {
    public static float calculate(Store store, Coordinates coordinates) {

        float distance = 0;


        Location loc1 = new Location("current location");
        loc1.setLatitude(coordinates.getLatitude());
        loc1.setLongitude(coordinates.getLongitude());

        Location loc2 = new Location("store location");

        loc2.setLatitude(store.latitude);
        loc2.setLongitude(store.longitude);

        distance = loc1.distanceTo(loc2);


        return distance;
    }


}
