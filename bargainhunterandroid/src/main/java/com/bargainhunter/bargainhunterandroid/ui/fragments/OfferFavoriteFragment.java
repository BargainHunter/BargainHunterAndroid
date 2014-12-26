package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.controllers.LocalDBController;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veruz on 20/12/2014.
 */
public class OfferFavoriteFragment extends OfferListFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private int mSectionNumber;

    private LocalDBController ctrl;

    public OfferFavoriteFragment(){
        // Required empty public constructor
    }

    public static OfferFavoriteFragment newInstance(int sectionNumber) {
        OfferFavoriteFragment fragment = new OfferFavoriteFragment();
        boolean priceFilters[] = null;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putBooleanArray(ARG_PRICE_FILTERS, priceFilters);
        fragment.setArguments(args);
        return fragment;
    }

    public static OfferFavoriteFragment newInstance(int sectionNumber, boolean priceFilters[]) {
        OfferFavoriteFragment fragment = new OfferFavoriteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putBooleanArray(ARG_PRICE_FILTERS, priceFilters);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ctrl = LocalDBController.getInstance(getActivity().getBaseContext());
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            // if newInstance(secNum, priceFilter) called
            if (getArguments().getBooleanArray(ARG_PRICE_FILTERS) != null)
                mPriceFilters = getArguments().getBooleanArray(ARG_PRICE_FILTERS);
        }

        if (mPriceFilters != null) {
            applyPriceFilters();
        } else {
            loadOfferList();
        }
    }

    @Override
    public void loadOfferList(){
        ctrl = LocalDBController.getInstance(getActivity().getBaseContext());

        Cursor resultSet = ctrl.getReadableDatabase().rawQuery("Select id from FavOffers ORDER BY id" , null);
        resultSet.moveToFirst();

        if(resultSet.getCount()==0) {
            // TODO print message  "no favs"
            offerList = new ArrayList<>();
        }else {
            String list = "(";
            // parsing returned ids and converting them to sql list

            if(resultSet.getCount()==1)
                list = "("+resultSet.getLong(0)+")";
            else {
                list += resultSet.getLong(0)+",";
                while (resultSet.moveToNext()) { // may skip first row - to_check
                    if (!resultSet.isLast())
                        list += resultSet.getLong(0) + ", ";
                    else
                        list += resultSet.getLong(0);
                }

                list += ")";

            }
            offerList = new Select().from(Offer.class).where("offer_id IN " + list).execute();
        }
    }

    @Override
    protected void applyPriceFilters() {
        offerList = new ArrayList<>();
        List<Offer> offerListToAdd;

        Cursor resultSet = ctrl.getReadableDatabase().rawQuery("Select id from FavOffers ORDER BY id" , null);
        resultSet.moveToFirst();
        String list = "";
        if(resultSet.getCount()==0) {
            // TODO print message  "no favs"
            offerList = new ArrayList<>();
        }else {
            list = "(";
            // parsing returned ids and converting them to sql list

            while (resultSet.moveToNext()) { // may skip first row - to_check
                if (!resultSet.isLast())
                    list += resultSet.getLong(1) + ", ";
                else
                    list += resultSet.getLong(1);
            }

            list += ")";


            if (mPriceFilters[0]) {
                offerListToAdd = new Select().from(Offer.class).where("offer_id IN " + list).execute();
                for (Offer offer : offerListToAdd) {
                    offerList.add(offer);
                }
            }
            if (mPriceFilters[1]) {
                offerListToAdd = new Select().from(Offer.class).where("offer_id IN " + list + " AND price BETWEEN ? AND ?", 0, 5).execute();
                for (Offer offer : offerListToAdd) {
                    offerList.add(offer);
                }
            }
            if (mPriceFilters[2]) {
                offerListToAdd = new Select().from(Offer.class).where("offer_id IN " + list + " AND price BETWEEN ? AND ?", 5, 10).execute();
                for (Offer offer : offerListToAdd) {
                    offerList.add(offer);
                }
            }
            if (mPriceFilters[3]) {
                offerListToAdd = new Select().from(Offer.class).where("offer_id IN " + list + " AND price BETWEEN ? AND ?", 10, 80).execute();
                for (Offer offer : offerListToAdd) {
                    offerList.add(offer);
                }
            }
            if (mPriceFilters[4]) {
                offerListToAdd = new Select().from(Offer.class).where("offer_id IN " + list + " AND price BETWEEN ? AND ?", 80, 100).execute();
                for (Offer offer : offerListToAdd) {
                    offerList.add(offer);
                }
            }
            if (mPriceFilters[5]) {
                offerListToAdd = new Select().from(Offer.class).where("offer_id IN " + list + " AND price > ?", 100).execute();
                for (Offer offer : offerListToAdd) {
                    offerList.add(offer);
                }
            }
        }
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

        updateDisplay(offerList);

        return view;
    }

    @Override
    public void onResume() {
        loadOfferList();
        updateDisplay(offerList);
        super.onResume();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onStoreFavoriteFragmentInteraction(String id);
    }
}
