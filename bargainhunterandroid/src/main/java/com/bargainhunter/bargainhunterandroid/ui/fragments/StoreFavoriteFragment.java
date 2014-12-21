package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;
import com.bargainhunter.bargainhunterandroid.ui.activities.MainActivity;

/**
 * Created by Veruz on 20/12/2014.
 */
public class StoreFavoriteFragment extends StoreListFragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    private int mSectionNumber;

    public StoreFavoriteFragment(){
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stores = null;
        stores = new Select().from(Store.class).execute();

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onStoreFavoriteFragmentInteraction(String id);
    }
}

