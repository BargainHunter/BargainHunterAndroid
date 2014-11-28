package com.bargainhunter.bargainhunterandroid;


import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class StoreFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STORE_ID = "param1";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_ENDPOINT = "endpoint";
    private static final String ARG_RADIUS = "radius";

    private String mStoreId;
    private int mSectionNumber;
    private String mEndpoint;
    private double mRadius;
    private MainActivity mContext;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param endpoint
     * @param sectionNumber
     * @param storeId
     * @param radius
     * @return A new instance of fragment StoreFragment.
     */
    public static StoreFragment newInstance(String endpoint,
                                            int sectionNumber,
                                            String storeId,
                                            double radius) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_RADIUS, radius);
        args.putString(ARG_ENDPOINT, endpoint);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_STORE_ID, storeId);
        fragment.setArguments(args);
        return fragment;
    }
    public StoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setContentView(R.layout.fragment_store_info_offer_list);
        if (getArguments() != null) {
            mEndpoint = getArguments().getString(ARG_ENDPOINT);
            mRadius = getArguments().getDouble(ARG_RADIUS);
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            mStoreId = getArguments().getString(ARG_STORE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_info_offer_list, container, false);

        return view;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (MainActivity) activity;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String endpoint,
                                          int sectionNumber,
                                          String storeId,
                                          double radius);
    }

}
