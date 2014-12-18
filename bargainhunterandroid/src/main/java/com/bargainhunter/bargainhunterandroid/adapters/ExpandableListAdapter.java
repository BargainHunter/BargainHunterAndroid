package com.bargainhunter.bargainhunterandroid.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.models.entities.ListChildItem;
import com.bargainhunter.bargainhunterandroid.models.entities.ListParentItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    // Define activity context
    private Context mContext;

    // ArrayList with all the parent objects
    private ArrayList<ListParentItem> mListDataParent;

    // Hashmap for keeping track of our checkbox check states
    private HashMap<Integer, boolean[]> mChildCheckStates;

    /*
     * Text to display (group and child)
     */
    private String groupText;
    private String childText;

    /*  Here's the constructor we'll use to pass in our calling
     *  activity's context and group items
    */
    @SuppressLint("UseSparseArrays")
    public ExpandableListAdapter(Context context,
                                          ArrayList<ListParentItem> listDataGroup) {
        mContext = context;
        mListDataParent = listDataGroup;

        // Initialize our hashmap containing our check states here
        mChildCheckStates = new HashMap<Integer, boolean[]>();
    }

    public HashMap<Integer, boolean[]> getChildCheckStates() {
        return mChildCheckStates;
    }

    /*
     * Sets parent and children states
     * We use it to FilterDialogFragment
     */
    public void setChildCheckStates(boolean childChanges[], int groupPosition) {
        for (Map.Entry<Integer, boolean[]> entry : mChildCheckStates.entrySet()) {
            int groupPos = entry.getKey();
            if (groupPos == groupPosition) {
                mChildCheckStates.put(groupPosition,childChanges);
            }
        }
        // with this magic method the expandable list updates, according the mChildCheckStates
        notifyDataSetChanged();
    }

    /*
     * Gets all parents and children
     * We use it to FilterDialogFragment
     */
    public ArrayList<ListParentItem> getListDataParent() {
        return this.mListDataParent;
    }

    @Override
    public int getGroupCount() {
        return mListDataParent.size();
    }

    /*
     * This defaults to "public object getGroup" if you auto import the methods
     * We use ListParentItem to avoid unnecessary casting!
    */
    @Override
    public ListParentItem getGroup(int groupPosition) {
        return mListDataParent.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        GroupViewHolder groupViewHolder;

        groupText = getGroup(groupPosition).getParentName();

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_filter_custom_group_row, null);

            // Initialize the GroupViewHolder defined at the bottom of this document
            groupViewHolder = new GroupViewHolder();

            groupViewHolder.mGroupText = (TextView) convertView.findViewById(R.id.groupTextView);

            convertView.setTag(groupViewHolder);
        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        /*
         * Initialize mChildCheckStates before expanding of group to avoid null pointer exception
         * initialization according if there is a child with defaultValue = true (No Filter)
         */
        if (!isExpanded) {
            boolean getChecked[] = new boolean[getChildrenCount(groupPosition)];

            for (int i = 0 ; i < getChecked.length ; i++) {
                if (mListDataParent.get(groupPosition).getChildren().get(i).isDefaultValue()) {
                    getChecked[i] = true;
                    ((CheckBox) mListDataParent.get(groupPosition).getChildren().get(i).getObject()).setChecked(true);
                }
                else {
                    getChecked[i] = false;
                    ((CheckBox) mListDataParent.get(groupPosition).getChildren().get(i).getObject()).setChecked(false);
                }
            }

            // add getChecked[] to the mChildCheckStates hashmap using mGroupPosition as the key
            mChildCheckStates.put(groupPosition, getChecked);
        }

        groupViewHolder.mGroupText.setText(groupText);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return  mListDataParent.get(groupPosition).getChildren().size();
    }

    /*
     * This defaults to "public object getChild" if you auto import the methods
     * We use ListChildItem to avoid unnecessary casting!
    */
    @Override
    public ListChildItem getChild(int groupPosition, int childPosition) {
        return mListDataParent.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;

        ChildViewHolder childViewHolder = new ChildViewHolder();

        childText = getChild(mGroupPosition, mChildPosition).getChildName();

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_filter_custom_child_row, null);

            childViewHolder = new ChildViewHolder();

            childViewHolder.mChildText = (TextView) convertView
                    .findViewById(R.id.childTextView);

            childViewHolder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkBox);

            convertView.setTag(childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView
                    .getTag();
        }

        childViewHolder.mChildText.setText(childText);

		/*
		 * We have to set the onCheckChangedListener to null
		 * before restoring check states because each call to
		 * "setChecked" is accompanied by a call to the
		 * onCheckChangedListener
		*/
        childViewHolder.mCheckBox.setOnCheckedChangeListener(null);

        if (mChildCheckStates.containsKey(mGroupPosition)) {
			/*
			 * if the hashmap mChildCheckStates<Integer, Boolean[]> contains
			 * the value of the parent view (group) of this child (aka, the key),
			 * then retrive the boolean array getChecked[]
			*/
            boolean getChecked[] = mChildCheckStates.get(mGroupPosition);

            // set the check state of this position's checkbox based on the
            // boolean value of getChecked[position]
            childViewHolder.mCheckBox.setChecked(getChecked[mChildPosition]);

        } else {

			/*
			 * if the hashmap mChildCheckStates<Integer, Boolean[]> does not
			 * contain the value of the parent view (group) of this child (aka, the key),
			 * (aka, the key), then initialize getChecked[] as a new boolean array
			 *  and set it's size to the total number of children associated with
			 *  the parent group.
			 *  initialization is done according defaultValue of group's children
			*/
            boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];

            for (int i = 0 ; i < getChecked.length ; i++) {
                if (mListDataParent.get(groupPosition).getChildren().get(i).isDefaultValue()) {
                    getChecked[i] = true;
                    ((CheckBox) mListDataParent.get(groupPosition).getChildren().get(i).getObject()).setChecked(true);
                }
                else {
                    getChecked[i] = false;
                    ((CheckBox) mListDataParent.get(groupPosition).getChildren().get(i).getObject()).setChecked(false);
                }
            }

            // add getChecked[] to the mChildCheckStates hashmap using mGroupPosition as the key
            mChildCheckStates.put(mGroupPosition, getChecked);

            // set the check state of this position's checkbox based on the
            // boolean value of getChecked[position]
            childViewHolder.mCheckBox.setChecked(getChecked[childPosition]);
        }

        childViewHolder.mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, getChecked);
                } else {
                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, getChecked);
                }
            }
        });

        return convertView;
    }

    /*
     * Must return true to let listener of FilterDialogFragment, work
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public final class GroupViewHolder {
        TextView mGroupText;
    }

    public final class ChildViewHolder {
        TextView mChildText;
        CheckBox mCheckBox;
    }
}