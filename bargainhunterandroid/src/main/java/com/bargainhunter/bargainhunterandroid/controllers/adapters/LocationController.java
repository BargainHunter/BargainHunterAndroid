package com.bargainhunter.bargainhunterandroid.controllers.adapters;

import android.content.Context;
import com.bargainhunter.bargainhunterandroid.gps.GPSTracker;
import com.bargainhunter.bargainhunterandroid.models.entities.Coordinates;

/**
 * Created by vasovourka on 11/8/14.
 */
public class LocationController {
    // create class object

//    List<Coordinates> phoneCoordinates;
    Coordinates phoneLoc=new Coordinates();
    public Coordinates findCoordinates(Context context ) {
        GPSTracker gps=new GPSTracker(context);

        // check if GPS enabled
        if (gps.canGetLocation()) {
            phoneLoc.setLatitude(gps.getLatitude());
            phoneLoc.setLongitude(gps.getLongitude());
            return phoneLoc;


        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
            return null;

        }
    }
}
