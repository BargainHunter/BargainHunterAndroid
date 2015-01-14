package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.directions.IRoutingListener;
import com.bargainhunter.bargainhunterandroid.directions.Route;
import com.bargainhunter.bargainhunterandroid.directions.Routing;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;
import com.bargainhunter.bargainhunterandroid.ui.activities.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements IRoutingListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_SECTION_NUMBER = "section_number";
    private LatLng storePosition;
    private SupportMapFragment fragment;
    PendingIntent pendingIntent;
    private int mSectionNumber;
    private OnFragmentInteractionListener mListener;
    private List<Store> storelist = new ArrayList<>();
    private GoogleMap map;
    private LatLng myPosition;
    SharedPreferences preferences;
    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(int sectionNumber) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        storelist = new Select().from(Store.class).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.OnCategoryGridViewFragmentInteractionListener(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));

//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, fragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (map == null) {
            map = fragment.getMap();
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Toast.makeText(getActivity(), "location has changed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            //TODO:change location tracking structure
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {

                Toast.makeText(getActivity(), "lat: " + location.getLatitude(), Toast.LENGTH_LONG).show();

                double latitude = location.getLatitude();

                double longitude = location.getLongitude();

                myPosition = new LatLng(latitude, longitude);

               /* map.addMarker(new MarkerOptions().position(myPosition).title("Phone Location").snippet("lat:" +
                        latitude + "\n" + "long:" + longitude));*/
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 17));

                Drawable iconDrawable = getResources().getDrawable(R.drawable.shop_icon);
                Bitmap iconBmp = ((BitmapDrawable) iconDrawable).getBitmap();
                LatLng point;
                List<Offer> tempOffers;
                int reqCode = 1;
                String stringOffers = "";

                for (Store store : storelist) {
                    tempOffers = store.getBranch().getOffers();
                    /*if (store.getAddress().equals("Tsimiski")) {
                        storePosition = new LatLng(store.getLatitude(), store.getLongitude());
                        Toast.makeText(getActivity(), store.getStoreName() + ":" + store.getAddress(), Toast.LENGTH_LONG).show();
                    }*/
                    for (Offer toffer : tempOffers) {
                        stringOffers = stringOffers.concat(toffer.getTitle() + "\n\n");
                    }
                    point = new LatLng(store.getLatitude(), store.getLongitude());
                    // Drawing marker on the map
                    drawMarker(store, iconBmp);
                    // Drawing circle on the map
                    drawCircle(point,getRadius("notificationRadius",getActivity()));
                    // This intent will call the activity ProximityActivity
                    Intent proximityIntent = new Intent("com.bargainhunter.activity.proximity");

                    // Passing latitude to the PendingActivity
                    proximityIntent.putExtra("lat", point.latitude);

                    // Passing longitude to the PendingActivity
                    proximityIntent.putExtra("lng", point.longitude);

                    proximityIntent.putExtra("storename", store.getStoreName());
                    proximityIntent.putExtra("reqcode", reqCode);
                    proximityIntent.putExtra("StoreOffers", stringOffers);
                    proximityIntent.putExtra("storeaddr", store.getAddress() + " " + store.getAddressNo());
                    // Creating a pending intent which will be invoked by LocationManager when the specified region is
                    // entered or exited
                    pendingIntent = PendingIntent.getActivity(getActivity().getBaseContext(), reqCode, proximityIntent, PendingIntent.FLAG_ONE_SHOT);
                    // Setting proximity alert
                    // The pending intent will be invoked when the device enters or exits the region 100 meters
                    // away from the marked point
                    // The -1 indicates that, the monitor will not be expired
                    locationManager.addProximityAlert(point.latitude, point.longitude, getRadius("notificationRadius",getActivity()), -1, pendingIntent);
                    reqCode++;
                    stringOffers = "";
                }
            }
            else {
                Toast.makeText(getActivity(), "location is null", Toast.LENGTH_LONG).show();
            }
            preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
            String show_on_map = preferences.getString("Show_on_map","0");
            String storeid = preferences.getString("StoreId","Not Found!");
            if (show_on_map.equals("1"))
            {
                Toast.makeText(getActivity(), storeid, Toast.LENGTH_SHORT).show();
                Store route_store=new Select().from(Store.class).where("store_id = ?",storeid).executeSingle();
                storePosition= new LatLng(route_store.getLatitude(), route_store.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(storePosition, 17));
                Routing routing = new Routing(Routing.TravelMode.WALKING);
                routing.registerListener(this);
                routing.execute(myPosition, storePosition);
            }


/*
            Routing routing = new Routing(Routing.TravelMode.WALKING);
            routing.registerListener(this);
            routing.execute(myPosition, storePosition);*/
        }

    }

    @Override
    public void onRoutingFailure() {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(PolylineOptions mPolyOptions, Route route) {
        PolylineOptions polyoptions = new PolylineOptions();
        polyoptions.color(Color.BLUE);
        polyoptions.width(10);
        polyoptions.addAll(mPolyOptions.getPoints());
        map.addPolyline(polyoptions);
    }

    public static float getRadius(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        float radius = (float)preferences.getInt(key,0);
        return radius;
    }
    private void drawCircle(LatLng point, float radius) {
        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(point);

        // Radius of the circle
        circleOptions.radius(radius);

        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK);

        // Border width of the circle
        circleOptions.strokeWidth(1);

        // Adding the circle to the GoogleMap
        map.addCircle(circleOptions);
    }

    private void drawMarker(Store store, Bitmap iconBmp) {
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(store.getLatitude(), store.getLongitude()))
                .title(store.getStoreName())
                .snippet(store.getAddress() + " " + store.getAddressNo() + "\n\n"
                        + store.getLatitude() + " " + store.getLongitude())
                .icon(BitmapDescriptorFactory.fromBitmap(iconBmp));
        // Adding marker on the Google Map
        map.addMarker(markerOptions);

    }

    @Override
    public void onDestroyView() {
        preferences  = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preferences.edit().putString("Show_on_map","0").commit();
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preferences.edit().putString("Show_on_map","0").commit();
        super.onPause();
    }

    @Override
    public void onDetach() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preferences.edit().putString("Show_on_map","0").commit();
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onMapFragmentInteraction(String id);
    }

}
