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
import com.bargainhunter.bargainhunterandroid.models.components.ListChildItem;
import com.bargainhunter.bargainhunterandroid.models.components.ListParentItem;
import com.bargainhunter.bargainhunterandroid.models.entities.Category;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
import com.bargainhunter.bargainhunterandroid.models.entities.OfferSubcategory;
import com.bargainhunter.bargainhunterandroid.models.entities.Subcategory;
import com.bargainhunter.bargainhunterandroid.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    protected static final String ARG_PRICE_FILTERS = "price_filters";
    private static final String ARG_CATEGORY_ID = "category_id";

    protected List<Offer> offerList;
    private int mSectionNumber;
    private OnFragmentInteractionListener mListener;
    private String mCategoryId;

    protected ArrayList<ListParentItem> mParent;
    /**
     * The fragment's ListView/GridView.
     */
    protected AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    protected ListAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OfferListFragment() {
    }

    public static OfferListFragment newInstance(int sectionNumber, String categoryId) {
        OfferListFragment fragment = new OfferListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    public void setParents(ArrayList<ListParentItem> parentItems) {
        this.mParent = parentItems;
        initializeOfferList();
        applyPriceFilters();
        updateDisplay(offerList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            mCategoryId = getArguments().getString(ARG_CATEGORY_ID);
        }

        initializeOfferList();

        if (mParent != null) {
            applyPriceFilters();
            applyCategoryFilters();
        }
    }

    // initializes offer list according what category the user choose, from grid view.
    protected void initializeOfferList() {
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

    protected void applyPriceFilters() {
        List<Offer> offerListToAdd = new ArrayList<>();

        ListParentItem parentItem = mParent.get(0);     // price filters
        ArrayList<ListChildItem> children = parentItem.getChildren();

        if (((CheckBox) children.get(1).getObject()).isChecked()) {
            for (Offer offer : offerList){
                if (((offer.getPrice()>=0) && (offer.getPrice()<=5)))
                    offerListToAdd.add(offer);
            }
        }

        if (((CheckBox) children.get(2).getObject()).isChecked()) {
            for (Offer offer : offerList){
                if (((offer.getPrice()>5) && (offer.getPrice()<=10)))
                    offerListToAdd.add(offer);
            }
        }

        if (((CheckBox) children.get(3).getObject()).isChecked()) {
            for (Offer offer : offerList){
                if (((offer.getPrice()>10) && (offer.getPrice()<=80)))
                    offerListToAdd.add(offer);
            }
        }

        if (((CheckBox) children.get(4).getObject()).isChecked()) {
            for (Offer offer : offerList){
                if (((offer.getPrice()>80) && (offer.getPrice()<=100)))
                    offerListToAdd.add(offer);
            }
        }

        if (((CheckBox) children.get(5).getObject()).isChecked()) {
            for (Offer offer : offerList){
                if ((offer.getPrice()>100))
                    offerListToAdd.add(offer);
            }
        }

        if (!(((CheckBox) children.get(0).getObject()).isChecked())) {
            offerList = new ArrayList<>();
            offerList = offerListToAdd;
        }
    }

    private void applyCategoryFilters() {
        List<Offer> offerListToAdd = new ArrayList<>();                 // array list of offers to add

        ListParentItem parentItem = mParent.get(1);                     // category filters
        ArrayList<ListChildItem> children = parentItem.getChildren();   // all children of category filters

        for ( int i = 1 ; i < children.size() ; i++) {                  // for every children except "No filter"
            if (((CheckBox) children.get(i).getObject()).isChecked()) { // if the checkbox is checked
                String childName = children.get(i).getChildName();      // get the offer description
                for (Offer offer : offerList){                          // for every offer in the offerList
                    if (offer.getDescription() == childName) {          // if the offer have the same description with the child name
                        offerListToAdd.add(offer);                      // add the offer to offerListToAdd
                    }
                }
            }
        }

        if (!(((CheckBox) children.get(0).getObject()).isChecked())) {
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

        DialogFragment dialogFragment = FilterDialogFragment.newInstance(2, mCategoryId);
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
