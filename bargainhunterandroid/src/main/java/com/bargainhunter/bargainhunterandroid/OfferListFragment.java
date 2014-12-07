package com.bargainhunter.bargainhunterandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.*;
import android.widget.*;
import com.bargainhunter.bargainhunterandroid.DAOs.OfferAPI;
import com.bargainhunter.bargainhunterandroid.controllers.adapters.LocationController;
import com.bargainhunter.bargainhunterandroid.controllers.adapters.OfferAdapter;
import com.bargainhunter.bargainhunterandroid.models.entities.Coordinates;
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
public class OfferListFragment extends ListFragment implements AbsListView.OnItemClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_ENDPOINT = "endpoint";
    private static final String ARG_RADIUS = "radius";

    private int mSectionNumber;
    private String mEndpoint;
    private double mRadius;

    private Coordinates phoneLoc;
    private LocationController controller;

    List<Offer> offerList;
    List<Offer> finalofferList;
    public double price=0;
    public int choice=0;
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

    public static OfferListFragment newInstance(int sectionNumber, String endpoint, double radius) {
        OfferListFragment fragment = new OfferListFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_RADIUS, radius);
        args.putString(ARG_ENDPOINT, endpoint);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OfferListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mRadius = getArguments().getDouble(ARG_RADIUS);
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            mEndpoint = getArguments().getString(ARG_ENDPOINT);
        }
        requestData();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.price_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upto_5:
                Toast.makeText(getActivity(),"Setting filter up to 5 euros",Toast.LENGTH_SHORT).show();
                choice=2;
                requestData();
                return true;
            case R.id.upto_10:
                Toast.makeText(getActivity(),"Setting filter between 5-10 euros",Toast.LENGTH_SHORT).show();
                choice=3;
                requestData();
                return true;
            case R.id.from_80:
                Toast.makeText(getActivity(),"Setting filter between 80-100 euros",Toast.LENGTH_SHORT).show();
                choice=4;
                requestData();
                return true;
            case R.id.from_100:
                Toast.makeText(getActivity(),"Setting filter to 100+ euros",Toast.LENGTH_SHORT).show();
                choice=5;
                requestData();
                return true;
            case R.id.no_filter:
                Toast.makeText(getActivity(),"No Filter Active",Toast.LENGTH_SHORT).show();
                choice=1;
                requestData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private List<Offer> applyFilter(List<Offer> mylist, double low, double high)
    {
        Offer temp = new Offer();
        int i=0;
        while(i<mylist.size())
        {
            temp = mylist.get(i);
            price = temp.getPrice();
            if (price<low || price >high){
                mylist.remove(i);
            }
            else i++;
        }
        return mylist;
    }
    private void requestData() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(mEndpoint)
                .build();

        //implement the api interface
        OfferAPI api = adapter.create(OfferAPI.class);

        controller=new LocationController();
        phoneLoc=controller.findCoordinates(getActivity());

        //connect to server and user getOffer.
        api.getOffers(phoneLoc.getLatitude(),
                phoneLoc.getLongitude(),
                mRadius,
                new Callback<List<Offer>>() {

                    //Here i can save my data if the connection was successful.
                    @Override
                    public void success(List<Offer> offers, Response response) {
                        offerList = offers;
                        finalofferList = offerList;
                        switch (choice)
                        {
                            case 0:case 1:
                                break;
                            case 2:
                                finalofferList=applyFilter(offerList,0,5.0);
                                break;
                            case 3:
                                finalofferList=applyFilter(offerList,5.0,10.0);
                                break;
                            case 4:
                                finalofferList=applyFilter(offerList,80.0,100.0);
                                break;
                            case 5:
                                finalofferList=applyFilter(offerList,100.1,Double.MAX_VALUE);
                                break;
                            default:
                                break;
                        }
                        updateDisplay(finalofferList);
                    }
                    //Here i can handle the Retrofit error. Connection unsuccessful.
                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    protected void updateDisplay(List<Offer> offerList){
        OfferAdapter adapter = new OfferAdapter(this.getActivity(), R.layout.fragment_offer_list,offerList);
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onOfferListFragmentInteraction(String id);
    }
}