package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.*;
import android.widget.*;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.adapters.OfferAdapter;
import com.bargainhunter.bargainhunterandroid.controllers.LocationController;
import com.bargainhunter.bargainhunterandroid.models.Coordinates;
import com.bargainhunter.bargainhunterandroid.models.entities.Category;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
import com.bargainhunter.bargainhunterandroid.models.entities.OfferSubcategory;
import com.bargainhunter.bargainhunterandroid.models.entities.Subcategory;
import com.bargainhunter.bargainhunterandroid.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class OfferListFragment extends ListFragment implements AbsListView.OnItemClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_PRICE_FILTERS = "price_filters";
    private static final String ARG_CATEGORY_ID = "category_id";
    private static int instanceUsed;

    private List<Offer> offerList;
    private int mSectionNumber;
    private Coordinates phoneLoc;
    private LocationController controller;
    private OnFragmentInteractionListener mListener;
    private boolean[] mPriceFilters;
    private DialogFragment dialogFragment;
    private String mCategoryId;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OfferListFragment() {
    }

    public static OfferListFragment newInstance(int sectionNumber, String categoryId) {
        instanceUsed = 1;
        OfferListFragment fragment = new OfferListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    public static OfferListFragment newInstance(int sectionNumber, String categoryId, boolean priceFilters[]) {
        instanceUsed = 2;
        OfferListFragment fragment = new OfferListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putBooleanArray(ARG_PRICE_FILTERS, priceFilters);
        args.putString(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            switch (instanceUsed) {
                case 1:
                    mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
                    mCategoryId = getArguments().getString(ARG_CATEGORY_ID);
                    break;
                case 2:
                    mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
                    mCategoryId = getArguments().getString(ARG_CATEGORY_ID);
                    mPriceFilters = getArguments().getBooleanArray(ARG_PRICE_FILTERS);
            }
        }

        initializeOfferList();

        if (mPriceFilters != null) {
            applyPriceFilters();
        }
    }

    // initializes offer list according what category the user choose, from grid view.
    private void initializeOfferList() {
        offerList = new ArrayList<>();
        // get the category with this id
        Category category = new Select().from(Category.class)
                .where("category_id = ?", mCategoryId)
                .executeSingle();
        // get the list of subcategories, of this category
        List<Subcategory> subcategories = category.getSubcategories();

        for(Subcategory subcategory : subcategories){
            Long subcategoryId = subcategory.getSubcategoryId();
            List<OfferSubcategory> offerSubcategories = new Select()
                    .from(OfferSubcategory.class)
                    .where("subcategory_id = ?", subcategoryId)
                    .execute();
            for ( OfferSubcategory offerSubcategory : offerSubcategories) {
                if (!offerList.contains(offerSubcategory.getOffer()))
                    offerList.add(offerSubcategory.getOffer());
            }
        }
    }

    private void applyPriceFilters() {
        List<Offer> offerListToAdd = new ArrayList<>();

        if (mPriceFilters[1]) {
            for (Offer offer : offerList){
                if (((offer.getPrice()>=0) && (offer.getPrice()<=5)))
                    offerListToAdd.add(offer);
            }
        }
        if (mPriceFilters[2]) {
            for (Offer offer : offerList){
                if (((offer.getPrice()>5) && (offer.getPrice()<=10)))
                    offerListToAdd.add(offer);
            }
        }
        if (mPriceFilters[3]) {
            for (Offer offer : offerList){
                if (((offer.getPrice()>10) && (offer.getPrice()<=80)))
                    offerListToAdd.add(offer);
            }
        }
        if (mPriceFilters[4]) {
            for (Offer offer : offerList){
                if (((offer.getPrice()>80) && (offer.getPrice()<=100)))
                    offerListToAdd.add(offer);
            }
        }
        if (mPriceFilters[5]) {
            for (Offer offer : offerList){
                if ((offer.getPrice()>100))
                    offerListToAdd.add(offer);
            }
        }

        if (!mPriceFilters[0]) {
            offerList = new ArrayList<>();
            offerList = offerListToAdd;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.filter_action, menu);
            super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.price_filter:
                showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void showDialog() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment filterDialog = getFragmentManager().findFragmentByTag("filter_dialog");
        if (filterDialog != null) {
            ft.remove(filterDialog);
        }
        ft.addToBackStack(null);

        dialogFragment = FilterDialogFragment.newInstance(2, mCategoryId);
        dialogFragment.show(ft, "filter_dialog");
    }



    protected void updateDisplay(List<Offer> offerList) {
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

        updateDisplay(offerList);

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
        Offer offer = (Offer) (getListAdapter()).getItem(position);
        long offerId = offer.getOfferId();
        if (null != mListener) {
            mListener.onOfferListFragmentInteraction(String.valueOf(offerId));
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onOfferListFragmentInteraction(String id);
    }
}
