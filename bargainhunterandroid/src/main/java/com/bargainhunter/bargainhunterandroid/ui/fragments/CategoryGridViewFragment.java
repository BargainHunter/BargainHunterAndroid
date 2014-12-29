package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.adapters.ImageAdapter;
import com.bargainhunter.bargainhunterandroid.models.entities.Category;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
import com.bargainhunter.bargainhunterandroid.ui.activities.MainActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.bargainhunter.bargainhunterandroid.ui.fragments.CategoryGridViewFragment.OnCategoryGridViewFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryGridViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryGridViewFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";

    private int mSectionNumber;

    GridView mGridView;

    List<Category> categories;

    private OnCategoryGridViewFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryGridViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryGridViewFragment newInstance(int sectionNumber) {
        CategoryGridViewFragment fragment = new CategoryGridViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryGridViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        // get categories from database
        categories = new Select().from(Category.class).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_category_grid_view, container, false);
        //set the adapter
        mGridView = (GridView) view.findViewById(R.id.categoryGridView);
        final ImageAdapter imageAdapter = new ImageAdapter(getActivity(), categories);
        mGridView.setAdapter(imageAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Category category = (Category) imageAdapter.getItem(position);
                Long categoryId = category.getCategoryId();
                if (null != mListener) {
                    mListener.OnCategoryGridViewFragmentInteractionListener(String.valueOf(categoryId));
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        try {
            mListener = (OnCategoryGridViewFragmentInteractionListener) activity;
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
    public interface OnCategoryGridViewFragmentInteractionListener {
        public void OnCategoryGridViewFragmentInteractionListener(String categoryName);
    }

}
