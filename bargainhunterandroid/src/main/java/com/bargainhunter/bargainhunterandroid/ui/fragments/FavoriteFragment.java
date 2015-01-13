package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.ui.activities.MainActivity;
/**
 * Created by Veruz on 21/12/2014.
 */
public class FavoriteFragment extends Fragment{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mSectionNumber;
    private TextView tv1, tv2;
    private OnFragmentInteractionListener mListener;
    public FavoriteFragment(){
        // Required empty public constructor
    }
    public static FavoriteFragment newInstance(int sectionNumber){
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    //TODO on click blue
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View mView = (View)inflater.inflate(R.layout.fragment_favorite, container, false);
        tv1 = (TextView) mView.findViewById(R.id.storeTextView);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment storeFavoriteFragment = StoreFavoriteFragment.newInstance(4);
                fragmentManager.beginTransaction()
                        .replace(container.getId(), storeFavoriteFragment, "favorite_stores_fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        tv2 = (TextView) mView.findViewById(R.id.offerTextView);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment offerFavoriteFragment = OfferFavoriteFragment.newInstance(4);
                fragmentManager.beginTransaction()
                        .replace(container.getId(), offerFavoriteFragment, "favorite_offers_fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        return mView;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFavoriteFragmentInteraction(Uri uri);
    }
}
