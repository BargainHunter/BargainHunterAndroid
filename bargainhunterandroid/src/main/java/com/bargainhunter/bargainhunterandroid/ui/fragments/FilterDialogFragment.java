package com.bargainhunter.bargainhunterandroid.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.adapters.ExpandableListAdapter;
import com.bargainhunter.bargainhunterandroid.models.components.ListChildItem;
import com.bargainhunter.bargainhunterandroid.models.components.ListParentItem;
import com.bargainhunter.bargainhunterandroid.models.entities.Category;
import com.bargainhunter.bargainhunterandroid.models.entities.Subcategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.bargainhunter.bargainhunterandroid.ui.fragments.FilterDialogFragment.OnDialogFilterFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilterDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FilterDialogFragment extends DialogFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_CATEGORY_ID = "category_id";

    private int mSectionNumber;
    private String mCategoryId;

    // all data for displaying
    private ArrayList<ListParentItem> mParent;

    // the expandablelistview and the adapter to manipulate data
    private ExpandableListAdapter mListAdapter;
    private ExpandableListView mExpandableListView;

    // check states!
    HashMap<Integer, boolean[]> mChildCheckStates;

    private OnDialogFilterFragmentInteractionListener mListener;

    public static FilterDialogFragment newInstance(int sectionNumber, String categoryId) {
        FilterDialogFragment fragment = new FilterDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }
    public FilterDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() == null)
            return;

        int dialogWidth = getResources().getDisplayMetrics().widthPixels;
        int dialogHeight = getResources().getDisplayMetrics().heightPixels - 50;

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            mCategoryId = getArguments().getString(ARG_CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_dialog, container, false);

        // Title of the dialog
        getDialog().setTitle("Chose Filters");

        mExpandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        mParent = new ArrayList<>();

        // create price filters
        prepareListData();
        // create subcategory filters
        prepareSubcategoryListData();

        mListAdapter = new ExpandableListAdapter(getActivity(), mParent);
        mExpandableListView.setAdapter(mListAdapter);

        mParent = mListAdapter.getListDataParent();             // get all parents from adapter
        mChildCheckStates = mListAdapter.getChildCheckStates(); // get all states from adapter

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                filterInteraction(groupPosition, childPosition, v);

                mChildCheckStates = mListAdapter.getChildCheckStates();
                return true;
            }
        });

        Button mCancelButton = (Button) view.findViewById(R.id.cancelButton);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Button mConfirmButton = (Button) view.findViewById(R.id.confirmButton);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    updateCheckBoxesOfParents();
                    mListener.onDialogFilterFragmentInteraction(mCategoryId, mParent);
                    getDialog().hide();
                }
            }
        });

        return view;
    }

    private void filterInteraction(int groupPosition, int childPosition, View v) {
        boolean selectedGroupChildrenCheckStates[] = mChildCheckStates.get(groupPosition); // get selected group's children states!
        ListParentItem priceFilterParent = mParent.get(groupPosition);          // the first Parent

        if (mParent.get(groupPosition) == priceFilterParent) {      // if user click the first Parent (Price Filter)
            List<ListChildItem> children = mParent.get(groupPosition).getChildren(); //all children of the first parent
            ListChildItem defaultChild = mParent.get(groupPosition).getChildren().get(0); // get the No Filter child

            ListChildItem child = mParent.get(groupPosition).getChildren().get(childPosition); // the selected child
            CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox); //the checkbox of the selected child

            if (child != defaultChild) {        // if the selected child is not the No Filter
                selectedGroupChildrenCheckStates[0] = false; // set No Filter state to false !
                mListAdapter.setChildCheckStates(selectedGroupChildrenCheckStates, groupPosition); // important! set states to change child view!!
                checkBox.toggle();          // change selected child checkbox status
                int count = 0;              // counts the number of children (except default) check state is false
                for (int i = 1; i < selectedGroupChildrenCheckStates.length; i++) {
                    if (!selectedGroupChildrenCheckStates[i])
                        count += 1;
                }
                if (count == (selectedGroupChildrenCheckStates.length - 1)) {  // if all children are false
                    selectedGroupChildrenCheckStates[0] = true; // set No Filter state to true !
                    mListAdapter.setChildCheckStates(selectedGroupChildrenCheckStates, groupPosition); // important! set states to change child view!!
                }
            }
            if (child == defaultChild) {        // if the selected child is the default (No Filter)
                checkBox.toggle();              // change checked state of No Filter child
                if (checkBox.isChecked()) {     // if No Filter child is checked
                    for (int i = 1; i < selectedGroupChildrenCheckStates.length; i++) {
                        selectedGroupChildrenCheckStates[i] = false;   // change the rest children to false
                        mListAdapter.setChildCheckStates(selectedGroupChildrenCheckStates, groupPosition); // important! set states to change child view!!
                    }
                } else {                        // if No Filter child is not checked
                    int count = 0;              // counts the number of children (except default) check state is false
                    for (int i = 1; i < selectedGroupChildrenCheckStates.length; i++) {
                        if (!selectedGroupChildrenCheckStates[i])
                            count += 1;
                    }
                    if (count == (selectedGroupChildrenCheckStates.length - 1))     // if all children are false
                        checkBox.toggle();                                          // keeps the No Filter child state to true
                }
            }
        }
    }

    private void updateCheckBoxesOfParents() {
        for (int i = 0 ; i < mParent.size() ; i++) {
            ArrayList<ListChildItem> children = (mParent.get(i)).getChildren();
            boolean[] checkStates = mChildCheckStates.get(i);
            for (int j = 0 ; j < children.size() ; j++) {
                CheckBox checkBox = new CheckBox(getActivity());
                checkBox.setChecked(checkStates[j]);
                mParent.get(i).getChildren().get(j).setObject(checkBox);
            }
        }
    }

    private void prepareListData() {
        //adding child data
        ArrayList<ListChildItem> children = new ArrayList<>();
        children.add(new ListChildItem("No Filter", true, new CheckBox(getActivity())));
        children.add(new ListChildItem("> 5 EUR", false, new CheckBox(getActivity())));
        children.add(new ListChildItem("5 - 10 EUR", false, new CheckBox(getActivity())));
        children.add(new ListChildItem("10 - 80 EUR", false, new CheckBox(getActivity())));
        children.add(new ListChildItem("80 - 100 EUR", false, new CheckBox(getActivity())));
        children.add(new ListChildItem("100 + EUR", false, new CheckBox(getActivity())));

        //adding parent data
        mParent.add(new ListParentItem("Price List", children));
    }

    private void prepareSubcategoryListData() {
        //adding child data
        Category category = new Select().from(Category.class).where("category_id = ? ", mCategoryId).executeSingle();
        List<Subcategory> subcategories = category.getSubcategories();

        ArrayList<ListChildItem> children = new ArrayList<>();
        children.add(new ListChildItem("No Filter", true, new CheckBox(getActivity())));
        for (Subcategory subcategory : subcategories) {
            children.add(new ListChildItem(subcategory.getDescription(), false, new CheckBox((getActivity()))));
        }

        //adding parent data
        mParent.add(new ListParentItem("Category List", children));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDialogFilterFragmentInteractionListener) activity;
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
    public interface OnDialogFilterFragmentInteractionListener {
        public void onDialogFilterFragmentInteraction(String categoryId,
                                                      ArrayList<ListParentItem> parentItems);
    }
}