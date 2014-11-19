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
import com.bargainhunter.bargainhunterandroid.models.APIs.OfferAPI;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OfferInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OfferInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class OfferInfoFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_OFFER_ID = "param1";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ENDPOINT = "http://192.168.1.2:8080" ;


    private String mOfferId;
    private int mSectionNumber;
    private Offer offer;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param offerId Parameter 1.
     * @return A new instance of fragment OfferInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OfferInfoFragment newInstance(int sectionNumber, String offerId) {
        OfferInfoFragment fragment = new OfferInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OFFER_ID, offerId);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public OfferInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mOfferId = getArguments().getString(ARG_OFFER_ID);
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        requestData();
    }

    //Call Retrofit to fetch data from server
    //and store data to an offer object.
    private void requestData() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        //implement the api interface
        OfferAPI api = adapter.create(OfferAPI.class);

        //connect to server and user getOffer.
        api.getOffer(Long.valueOf(getArguments().getString(ARG_OFFER_ID)) , new Callback<Offer>() {

            //Here i can save my data if the connection was successful.
            @Override
            public void success(Offer arg0, Response response) {
                offer = arg0;
                updateDisplay(offer);

            }

            //Here i can handle the Retrofit error. Connection unsuccessful.
            @Override
            public void failure(RetrofitError arg0) {

                Toast.makeText(getActivity(),arg0.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Updates the display!
    private void updateDisplay(Offer offer) {

        TextView titleView = (TextView) getView().findViewById(R.id.titleView);
        titleView.setText(offer.getTitle());

        TextView descriptionView = (TextView) getView().findViewById(R.id.descriptionView);
        descriptionView.setText(offer.getDescription());

        TextView priceView = (TextView) getView().findViewById(R.id.priceView);
        priceView.setText(String.valueOf(offer.getPrice()));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_info, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onOfferInfoFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
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
        public void onOfferInfoFragmentInteraction(Uri uri);
    }

}
