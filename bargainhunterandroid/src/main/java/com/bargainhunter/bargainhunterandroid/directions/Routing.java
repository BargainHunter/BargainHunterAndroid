package com.bargainhunter.bargainhunterandroid.directions;

import android.os.AsyncTask;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasovourka on 12/16/14.
 */
public class Routing extends AsyncTask<LatLng, Void, Route> {
    private List<IRoutingListener> listeners;
    private TravelMode mTravelMode;

    public Routing(TravelMode mTravelMode) {
        this.listeners = new ArrayList<IRoutingListener>();
        this.mTravelMode = mTravelMode;
    }

    public void registerListener(IRoutingListener mListener) {
        listeners.add(mListener);
    }

    protected void dispatchOnStart() {
        for (IRoutingListener mListener : listeners) {
            mListener.onRoutingStart();
        }
    }

    protected void dispatchOnFailure() {
        for (IRoutingListener mListener : listeners) {
            mListener.onRoutingFailure();
        }
    }

    protected void dispatchOnSuccess(PolylineOptions mOptions, Route route) {
        for (IRoutingListener mListener : listeners) {
            mListener.onRoutingSuccess(mOptions, route);
        }
    }

    @Override
    protected Route doInBackground(LatLng... aPoints) {
        for (LatLng mPoint : aPoints) {
            if (mPoint == null) return null;
        }
        //third parameter in Asynctask<>
        Route r = null;
        try {
            return r = new GoogleParser(constructURL(aPoints)).parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    protected String constructURL(LatLng... points) {
        LatLng start = points[0];
        LatLng dest = points[1];
        String sJsonURL = "http://maps.googleapis.com/maps/api/directions/json?";

        final StringBuffer mBuf = new StringBuffer(sJsonURL);
        mBuf.append("origin=");
        mBuf.append(start.latitude);
        mBuf.append(',');
        mBuf.append(start.longitude);
        mBuf.append("&destination=");
        mBuf.append(dest.latitude);
        mBuf.append(',');
        mBuf.append(dest.longitude);
        mBuf.append("&sensor=true&mode=");
        mBuf.append(mTravelMode.getValue());

        return mBuf.toString();
    }

    @Override
    protected void onPreExecute() {
        dispatchOnStart();
    }

    //what is returned from doInBackground()( aRoute obj)
    @Override
    protected void onPostExecute(Route result) {
        if (result == null) {
            dispatchOnFailure();
        } else {
            PolylineOptions mOptions = new PolylineOptions();

            for (LatLng point : result.getPoints()) {
                mOptions.add(point);
            }

            dispatchOnSuccess(mOptions, result);
        }
    }

    public enum TravelMode {
        BIKING("biking"),
        DRIVING("driving"),
        WALKING("walking"),
        TRANSIT("transit");

        protected String sValue;

        private TravelMode(String sValue) {
            this.sValue = sValue;
        }

        protected String getValue() {
            return sValue;
        }
    }

}
