package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.ui.activities.MainActivity;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoreInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StoreInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreInfoFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STORE_ID = "param1";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_ENDPOINT = "endpoint";

    private String mStoreId;
    private int mSectionNumber;

    private OnFragmentInteractionListener mListener;

    private Store store;

    public StoreInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber Parameter 1.
     * @param storeId       Parameter 2.
     * @return A new instance of fragment StoreInfoFragment.
     */
    public static StoreInfoFragment newInstance(int sectionNumber, String storeId) {
        StoreInfoFragment fragment = new StoreInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STORE_ID, storeId);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mStoreId = getArguments().getString(ARG_STORE_ID);
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        store = new Select().from(Store.class).where("store_id = ?", mStoreId).executeSingle();
    }

    //Updates the display!
    private void updateDisplay(View view, Store store) {
        TextView storeNameView = (TextView) view.findViewById(R.id.storeNameView);
        storeNameView.setText(store.storeName);

        TextView cityView = (TextView) view.findViewById(R.id.cityView);
        cityView.setText(store.city);

        TextView addressView = (TextView) view.findViewById(R.id.addressView);
        addressView.setText(store.address + " " + store.addressNo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_info, container, false);

        updateDisplay(view, store);

        // Inflate the layout for this fragment
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
        // TODO: Update argument type and name
        public void onStoreInfoFragmentInteraction(Uri uri);
    }

}