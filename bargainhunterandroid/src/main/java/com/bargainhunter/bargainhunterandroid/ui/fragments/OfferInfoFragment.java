package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.controllers.LocalDBController;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
import com.bargainhunter.bargainhunterandroid.ui.activities.MainActivity;


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

    private LocalDBController ctrl;

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
        setHasOptionsMenu(true);


        ctrl = LocalDBController.getInstance(getActivity().getBaseContext());

        if (getArguments() != null) {
            mOfferId = getArguments().getString(ARG_OFFER_ID);
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        offer = new Select().from(Offer.class).where("offer_id = ?", mOfferId).executeSingle();
    }

    public void addStoreListFragragment() {
        Fragment storeListFromOfferFragment = StoreListFromOfferFragment.newInstance(1, mOfferId);

        getChildFragmentManager().beginTransaction().add(R.id.StoreFragmentContainer, storeListFromOfferFragment).commit();
        getChildFragmentManager().executePendingTransactions();
    }

    //Updates the display!
    private void updateDisplay(View view, Offer offer) {

        addStoreListFragragment();

        TextView titleView = (TextView) view.findViewById(R.id.titleView);
        titleView.setText(offer.getTitle());

        TextView descriptionView = (TextView) view.findViewById(R.id.descriptionView);
        descriptionView.setText(offer.getDescription());

        TextView priceView = (TextView) view.findViewById(R.id.priceView);
        priceView.setText(offer.getPrice().toString());

        TextView colorLabel = (TextView) view.findViewById(R.id.colorLabel);

        final ScrollView scroll = (ScrollView) view.findViewById(R.id.scrollView);
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        /**
         * Sets a TextView at the bottom of the Fragment that changes accordingly with
         * the discount of the Bargain in question .
         * perc = Percentage of how much cheaper the Item in question is
         * percHot = threshold for a deal to be considered Hot
         * percGreat = threshold for a deal to be considered great or above average
         */
        double perc = (offer.getOldPrice()-offer.getPrice())/offer.getOldPrice();
        double percHot = 0.3;
        double percGreat = 0.2;
        if( perc >= percHot ) {
            colorLabel.setText("HOT Deal!");
            colorLabel.setBackgroundResource(R.drawable.title_red);
        }else if (perc >=percGreat ) {
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

    /**
     *Creates the initial options menu with Favorite Icon on the actionbar of the fragment
     */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.favorite_action, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * If the offer hasn't been added to the favorites table beforehand it wil default to OFF
     * In case the offer in question had been added to the favorites table beforehand, the icon is set to ON
     */

    @Override
    public void onPrepareOptionsMenu (Menu menu){
        //TODO: result analogous with SQL result
        Cursor resultSet = ctrl.getReadableDatabase().rawQuery("Select id from FavOffers WHERE id = " + offer.getOfferId(), null);
        resultSet.moveToFirst();
        if(resultSet.getCount()==0) {
            menu.getItem(menu.size()-1).setIcon(getView().getResources().getDrawable(R.drawable.btn_star_big_off_disable));
            //iv.setImageDrawable(getView().getResources().getDrawable(R.drawable.favourites));
        }else{
            menu.getItem(menu.size()-1).setIcon(getView().getResources().getDrawable(R.drawable.btn_star_big_on));
            //iv.setImageDrawable(getView().getResources().getDrawable(R.drawable.red));
        }
    }

    /**
     * Once the user selects the Favorite icon, the offer ID will be saved to local table while
     * the icon will change to ON and a toast confirming the action will be displayed.
     * An already favorited offer will simply be removed from the table with an appropriate change
     * of icon and toast.
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite:
                //TODO: enter offer into favorite table
                Cursor resultSet = ctrl.getReadableDatabase().rawQuery("Select id from FavOffers WHERE id = " + offer.getOfferId(), null);
                resultSet.moveToFirst();
                if(resultSet.getCount()==0) {
                    ctrl.getReadableDatabase().execSQL("INSERT INTO FavOffers VALUES("+offer.getOfferId()+")");
                    item.setIcon(getView().getResources().getDrawable(R.drawable.btn_star_big_on));
                    Toast.makeText(getActivity(),"Added to Favorites",Toast.LENGTH_SHORT).show();
                }else{
                    ctrl.getReadableDatabase().execSQL("DELETE FROM FavOffers WHERE id="+offer.getOfferId());
                    item.setIcon(getView().getResources().getDrawable(R.drawable.btn_star_big_off_disable));
                    Toast.makeText(getActivity(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
