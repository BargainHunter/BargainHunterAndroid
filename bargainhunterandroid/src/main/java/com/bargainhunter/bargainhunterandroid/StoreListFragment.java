package com.bargainhunter.bargainhunterandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bargainhunter.bargainhunterandroid.DAOs.StoreAPI;
import com.bargainhunter.bargainhunterandroid.controllers.adapters.LocationController;
import com.bargainhunter.bargainhunterandroid.controllers.adapters.StoreAdapter;
import com.bargainhunter.bargainhunterandroid.controllers.adapters.StoreListController;
import com.bargainhunter.bargainhunterandroid.models.entities.Coordinates;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;
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
public class StoreListFragment extends ListFragment implements AbsListView.OnItemClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_ENDPOINT = "endpoint";

    private int mSectionNumber;
    private String mEndpoint;

    List<Store> storeList;

    private OnFragmentInteractionListener mListener;

    private StoreListController storeListController = new StoreListController();

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    public static StoreListFragment newInstance(int sectionNumber, String endpoint) {
        StoreListFragment fragment = new StoreListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ENDPOINT, endpoint);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StoreListFragment() {
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
       // super.onListItemClick(l, v, position, id);
        Store store=(Store)(getListAdapter()).getItem(position);
        Toast.makeText(getActivity(),store.getStoreName()+ "was clicked", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            mEndpoint = getArguments().getString(ARG_ENDPOINT);
        }
        storeList = storeListController.requestData(mEndpoint,this.getActivity());

        if (storeList != null) {
            updateDisplay(storeList);
        }
    }

    Coordinates phoneLoc;
    protected Coordinates getLocation(){
        LocationController controller=new LocationController();
        phoneLoc=controller.findCoordinates(this.getActivity());
        return phoneLoc;
    }

    protected void updateDisplay(List<Store> storeList){
        StoreAdapter adapter = new StoreAdapter(this.getActivity(), R.layout.fragment_store, storeList,getLocation());
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store, container, false);
        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onStoreListFragmentInteraction(storeList.get(position).getStoreId().toString());
        }
        Log.d("MainActivity", "Selected Store Id" + storeList.get(position).getStoreId().toString());
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
        // TODO: Update argument type and name
        public void onStoreListFragmentInteraction(String id);
    }

}