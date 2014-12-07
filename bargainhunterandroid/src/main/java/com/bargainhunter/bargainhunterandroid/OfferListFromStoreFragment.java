package com.bargainhunter.bargainhunterandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bargainhunter.bargainhunterandroid.DAOs.OfferAPI;
import com.bargainhunter.bargainhunterandroid.controllers.adapters.OfferAdapter;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;
/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class OfferListFromStoreFragment extends ListFragment implements AbsListView.OnItemClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STORE_ID = "store_id";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_ENDPOINT="endpoint";

    private String mStoreId;
    private int mSectionNumber;
    private String mEndpoint;

    List<Offer> offerList;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    public static OfferListFromStoreFragment newInstance(int sectionNumber, String endpoint, String storeId) {
        OfferListFromStoreFragment fragment = new OfferListFromStoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STORE_ID, storeId);
        args.putString(ARG_ENDPOINT, endpoint);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OfferListFromStoreFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStoreId = getArguments().getString(ARG_STORE_ID);
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            mEndpoint = getArguments().getString(ARG_ENDPOINT);
        }
        requestData();
    }

    private void requestData() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(mEndpoint)
                .build();

        //implement the api interface
        OfferAPI api = adapter.create(OfferAPI.class);

        //connect to server and user getOffer.
        api.getOffersFromStore(Long.valueOf(mStoreId).longValue(), new Callback<List<Offer>>() {

            //Here i can save my data if the connection was successful.
            @Override
            public void success(List<Offer> offers, Response response) {
                offerList = offers;
                updateDisplay(offerList);
            }

            //Here i can handle the Retrofit error. Connection unsuccessful.
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void updateDisplay(List<Offer> offerList){
        OfferAdapter adapter = new OfferAdapter(this.getActivity(), R.layout.fragment_offer_list, offerList);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_offer_list, container, false);
        //set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Offer offer = (Offer)(getListAdapter()).getItem(position);
        long offerId = offer.getOfferId();
        if (null != mListener) {
            mListener.onOfferListFromStoreFragmentInteraction(String.valueOf(offerId));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
        public void onOfferListFromStoreFragmentInteraction(String id);
    }

}
