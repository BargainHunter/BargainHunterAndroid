package com.bargainhunter.bargainhunterandroid;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;

public class OfferAdapter extends ArrayAdapter<Offer>{
    private Context context;
    private List<Offer> offers;

    public OfferAdapter(Context context, int resource, List<Offer> offers) {
        super(context, resource, offers);
        this.context = context;
        this.offers = offers;
        ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_item_list, parent, false);

        Offer offer = offers.get(position);
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText(offer.getTitle());

        return view;
    }
}