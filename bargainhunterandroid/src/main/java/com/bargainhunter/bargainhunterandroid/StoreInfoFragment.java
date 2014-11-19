package com.bargainhunter.bargainhunterandroid;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bargainhunter.bargainhunterandroid.models.APIs.StoreAPI;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoreInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StoreInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class StoreInfoFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STORE_ID = "param1";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ENDPOINT = "http://192.168.1.65:8080" ;

    private String mStoreId;
    private int mSectionNumber;
    private Store store;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoreInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreInfoFragment newInstance(int sectionNumber, String storeId) {
        StoreInfoFragment fragment = new StoreInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STORE_ID, storeId);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public StoreInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStoreId = getArguments().getString(ARG_STORE_ID);
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    //Call Retrofit to fetch data from server
    //and store data to an offer object.
    private void requestData() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        //implement the api interface
        StoreAPI api = adapter.create(StoreAPI.class);

        //connect to server and user getOffer.
        api.getStore(Long.valueOf(getArguments().getString(ARG_STORE_ID)), new Callback<Store>() {

            //Here i can save my data if the connection was successful.
            @Override
            public void success(Store arg0, Response response) {
                store = arg0;
                updateDisplay(store);

            }

            //Here i can handle the Retrofit error. Connection unsuccessful.
            @Override
            public void failure(RetrofitError arg0) {

                Toast.makeText(getActivity(), arg0.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Updates the display!
    private void updateDisplay(Store store) {

        TextView storeNameView = (TextView) getView().findViewById(R.id.storeNameView);
        storeNameView.setText(store.getStoreName());

        TextView cityView = (TextView) getView().findViewById(R.id.cityView);
        cityView.setText(store.getCity());

        TextView addressView = (TextView) getView().findViewById(R.id.addressView);
        addressView.setText(String.valueOf(store.getAddress()));

        TextView addressNumView = (TextView) getView().findViewById(R.id.addressNumView);
        addressNumView.setText(String.valueOf(store.getAddressNo()));

        TextView latitudeView = (TextView) getView().findViewById(R.id.latitudeView);
        latitudeView.setText(String.valueOf(store.getLatitude()));

        TextView longitudeView = (TextView) getView().findViewById(R.id.longitudeView);
        longitudeView.setText(String.valueOf(store.getLongitude()));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_info, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
