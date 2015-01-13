package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.models.entities.FavoriteStores;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veruz on 20/12/2014.
 */
public class StoreFavoriteFragment extends StoreListFragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    private int mSectionNumber;

    public StoreFavoriteFragment(){
        // Required empty public constructor
    }

    public static StoreFavoriteFragment newInstance(int sectionNumber) {
        StoreFavoriteFragment fragment = new StoreFavoriteFragment();

        Bundle args = new Bundle();

        args.putInt(ARG_SECTION_NUMBER, sectionNumber);

        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        loadStoreList();
    }

    @Override
    public void loadStoreList(){

        List<FavoriteStores> resultSet = new Select().from(FavoriteStores.class).orderBy("store_id ASC").execute();
        //ctrl = LocalDBController.getInstance(getActivity().getBaseContext());

        //Cursor resultSet = ctrl.getReadableDatabase().rawQuery("Select id from FavStores ORDER BY id" , null);
        //resultSet.moveToFirst();

        if(resultSet.size()==0) {
            // TODO print message  "no favs"
            stores = new ArrayList<>();
        }else {
            String list = "(";
            // parsing returned ids and converting them to sql list

            if(resultSet.size()==1)
                list = "("+resultSet.get(0).getStoreId()+")";
            else {
                for (int i=0;i<resultSet.size();i++) {
                    if (i==resultSet.size()-1)
                        list += resultSet.get(i).getStoreId() + ", ";
                    else
                        list += resultSet.get(i).getStoreId();
                }

                list += ")";

            }
            stores = new Select().from(Store.class).where("store_id IN " + list).execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store_list, container, false);
        //set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

        updateDisplay(stores);

        return view;
    }

    @Override
    public void onResume() {
        loadStoreList();
        updateDisplay(stores);
        super.onResume();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onStoreFavoriteFragmentInteraction(String id);
    }
}

