package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.controllers.LocalDBController;
import com.bargainhunter.bargainhunterandroid.models.components.ListChildItem;
import com.bargainhunter.bargainhunterandroid.models.components.ListParentItem;
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
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ctrl = LocalDBController.getInstance(getActivity().getBaseContext());
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        initializeOfferList();
    }

    @Override
    public void initializeOfferList(){
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.filter);
        item.setVisible(false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
