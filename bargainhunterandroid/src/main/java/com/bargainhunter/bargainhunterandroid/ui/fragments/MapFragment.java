package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;
import com.bargainhunter.bargainhunterandroid.ui.activities.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mSectionNumber;
    private OnFragmentInteractionListener mListener;
    private List<Store> storelist = new ArrayList<>();
    private GoogleMap map;
    private LatLng myPosition;
    SupportMapFragment fragment;
    LocationManager locationManager;
    PendingIntent pendingIntent;
    SharedPreferences sharedPreferences;
    int locationCount = 0;

    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(int sectionNumber) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
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
//            mListener.onFragmentInteraction(uri);
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
            locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {

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
                /*Store testStore = new Store((long)8,"Mikel","Thessaloniki","Papanastasiou","56",40.612390, 22.964106, new Branch((long)1));
                storelist.add(testStore);
                testStore = new Store((long)9,"Mikel2","Thessaloniki","Papanastasiou","56",40.612571, 22.964959, new Branch((long)1));
                storelist.add(testStore);*/
                for (Store store : storelist) {
                    tempOffers = store.getBranch().getOffers();
                    for (Offer toffer : tempOffers) {
                        stringOffers = stringOffers.concat(toffer.getTitle() + "\n\n");
                    }
                    point = new LatLng(store.getLatitude(), store.getLongitude());
                    // Drawing marker on the map
                    drawMarker(store, iconBmp);
                    // Drawing circle on the map
                    drawCircle(point);
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
                    locationManager.addProximityAlert(point.latitude, point.longitude, 200, -1, pendingIntent);
                    reqCode++;
                    stringOffers = "";
                }
            }
        }
    }


    private void drawCircle(LatLng point) {
        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(point);

        // Radius of the circle
        circleOptions.radius(200);

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
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
