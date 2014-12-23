package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.os.Bundle;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;

/**
 * Created by Veruz on 20/12/2014.
 */
public class OfferFavoriteFragment extends OfferListFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private int mSectionNumber;

    public OfferFavoriteFragment(){
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        offerList = null;
        offerList = new Select().from(Offer.class).execute();

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onStoreFavoriteFragmentInteraction(String id);
    }
}
