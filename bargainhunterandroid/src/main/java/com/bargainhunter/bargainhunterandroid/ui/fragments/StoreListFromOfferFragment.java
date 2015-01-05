package com.bargainhunter.bargainhunterandroid.ui.fragments;


import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.adapters.StoreAdapter;
import com.bargainhunter.bargainhunterandroid.controllers.LocationController;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;
import com.bargainhunter.bargainhunterandroid.ui.activities.MainActivity;

import java.util.List;

public class StoreListFromOfferFragment extends ListFragment implements AbsListView.OnItemClickListener{
    private static final String ARG_OFFER_ID = "offer_id";
    private static final String ARG_SECTION_NUMBER = "section_number";
    List<Store> storeList;
    private String mOfferId;
    private int mSectionNumber;
    private OnFragmentInteractionListener mListener;

    private AbsListView mListView;

    private ListAdapter mAdapter;

    public StoreListFromOfferFragment(){
    }

    public static StoreListFromOfferFragment newInstance(int sectionNumber, String offerId) {
        StoreListFromOfferFragment fragment = new StoreListFromOfferFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OFFER_ID, offerId);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mOfferId = getArguments().getString(ARG_OFFER_ID);
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        Offer offer = new Select().from(Offer.class).where("offer_id = ?",mOfferId).executeSingle();
        storeList = offer.getBranch().getStores();
    }

    protected void updateDisplay(List<Store> storeList)
    {
        StoreAdapter adapter = new StoreAdapter(
                this.getActivity(),
                R.layout.fragment_store_list,
                storeList, new LocationController().findCoordinates(getActivity()));
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store_list,container,false);
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        updateDisplay(storeList);

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
        Store store = (Store) (getListAdapter().getItem(position));
        long storeId = store.getStoreId();
        if (null != mListener){
            mListener.onStoreListFromOfferFragmentInteraction(String.valueOf(storeId));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void setEmptyText(CharSequence text) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(text);
        }
    }

    public interface OnFragmentInteractionListener {
        public void onStoreListFromOfferFragmentInteraction(String id);
    }
}
