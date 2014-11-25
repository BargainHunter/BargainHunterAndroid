package com.bargainhunter.bargainhunterandroid.controllers.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.bargainhunter.bargainhunterandroid.MainActivity;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.gps.DistanceCalc;
import com.bargainhunter.bargainhunterandroid.models.entities.Coordinates;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Vasilis on 19/11/2014.
 */
public class StoreAdapter extends ArrayAdapter<Store>{

    private Context context;
    private List<Store> storeList;
    MainActivity activity;
    Coordinates phoneLoc;
   // LocationController controller;

    public StoreAdapter(Context context, int resource, List<Store> objects,Coordinates phoneLoc) {
        super(context, resource, objects);
        this.context = context;
        this.storeList = objects;
//        controller=new LocationController();
//        phoneLoc=controller.findCoordinates(context);
        this.phoneLoc=phoneLoc;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_store_custom, parent, false);


        Store store = storeList.get(position);
        TextView tv =(TextView) view.findViewById(R.id.storeTitle);
        tv.setText(store.getStoreName());



        TextView tv2=(TextView) view.findViewById(R.id.storeDistance);
      // phoneLoc=activity.getPhoneLoc();
        if(phoneLoc!=null){
            float dist= DistanceCalc.calculate(store, phoneLoc);
            DecimalFormat numberFormat = new DecimalFormat("##");
            tv2.setText("distance from store: " +numberFormat.format(dist) );
        }
        else{
        tv2.setText("cant get location " );
        }
        return view;
    }
}
