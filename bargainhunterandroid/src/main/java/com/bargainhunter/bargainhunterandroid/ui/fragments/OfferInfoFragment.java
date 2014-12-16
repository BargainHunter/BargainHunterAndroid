package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.ui.activities.MainActivity;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OfferInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OfferInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfferInfoFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_OFFER_ID = "param1";
    private static final String ARG_SECTION_NUMBER = "section_number";

    private String mOfferId;
    private int mSectionNumber;

    private Offer offer;

    private OnFragmentInteractionListener mListener;

    public OfferInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param offerId Parameter 1.
     * @return A new instance of fragment OfferInfoFragment.
     */
    public static OfferInfoFragment newInstance(int sectionNumber, String offerId) {
        OfferInfoFragment fragment = new OfferInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OFFER_ID, offerId);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mOfferId = getArguments().getString(ARG_OFFER_ID);
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        offer = new Select().from(Offer.class).where("offer_id = ?", mOfferId).executeSingle();
    }

    //Updates the display!
    private void updateDisplay(View view, Offer offer) {

        TextView titleView = (TextView) view.findViewById(R.id.titleView);
        titleView.setText(offer.getTitle());

        TextView descriptionView = (TextView) view.findViewById(R.id.descriptionView);
        descriptionView.setText(offer.getDescription());

        TextView priceView = (TextView) view.findViewById(R.id.priceView);
        priceView.setText(offer.getPrice().toString());

        TextView colorLabel = (TextView) view.findViewById(R.id.colorLabel);
        double perc = (offer.getOldPrice()-offer.getPrice())/offer.getOldPrice();
        double perc30 = 0.3;
        double perc20 = 0.2;
        if( perc >= perc30 ) {
            colorLabel.setText("HOT Deal!");
            colorLabel.setBackgroundResource(R.drawable.title_red);
        }else if (perc >=perc20 ) {
            colorLabel.setText("Great Deal");
            colorLabel.setBackgroundResource(R.drawable.title_orange);

        }else{
            colorLabel.setText("Good Deal");
            colorLabel.setBackgroundResource(R.drawable.title_green);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_info, container, false);

        updateDisplay(view, offer);

        return view;
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onOfferInfoFragmentInteraction(Uri uri);
    }

}
