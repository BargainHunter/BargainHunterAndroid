package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bargainhunter.bargainhunterandroid.R;

/**
 * Created by Veruz on 21/12/2014.
 */
public class FavoriteFragment extends Fragment{

    private static final String ARG_SECTION_NUMBER = "section_number";

    private int mSectionNumber;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View mView = (View)inflater.inflate(R.layout.fragment_favorite, container, false);

        TextView tv1 = (TextView) mView.findViewById(R.id.storeTextView);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment storeFavoriteFragment = StoreFavoriteFragment.newInstance(5);

                fragmentManager.beginTransaction()
                        .replace(container.getId(), storeFavoriteFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        TextView tv2 = (TextView) mView.findViewById(R.id.offerTextView);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment offerFavoriteFragment = OfferFavoriteFragment.newInstance(10);

                fragmentManager.beginTransaction()
                        .replace(container.getId(), offerFavoriteFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return mView;
    }


}
