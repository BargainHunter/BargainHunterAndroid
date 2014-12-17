package com.bargainhunter.bargainhunterandroid.directions;

import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by vasovourka on 12/16/14.
 */
public interface IRoutingListener {
    public void onRoutingFailure();

    public void onRoutingStart();

    public void onRoutingSuccess(PolylineOptions mPolyOptions, Route route);
}
