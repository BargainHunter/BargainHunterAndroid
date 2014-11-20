package com.bargainhunter.bargainhunterandroid.controllers.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;

import java.util.List;

/**
 * Created by Vasilis on 19/11/2014.
 */
public class StoreAdapter extends ArrayAdapter<Store>{

    private Context context;
    private List<Store> storeList;

    public StoreAdapter(Context context, int resource, List<Store> objects) {
        super(context, resource, objects);
        this.context = context;
        this.storeList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_store, parent, false);


        Store store = storeList.get(position);
        TextView tv =(TextView) view.findViewById(R.id.storeListTextView);
        tv.setText(store.getStoreName());

        return view;
    }
}
