package com.bargainhunter.bargainhunterandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;

import java.util.List;

public class OfferAdapter extends ArrayAdapter<Offer> {
    private Context context;
    private List<Offer> offers;

    public OfferAdapter(Context context, int resource, List<Offer> offers) {
        super(context, resource, offers);
        this.context = context;
        this.offers = offers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_offer_custom, parent, false);

        Offer offer = offers.get(position);
        TextView tv = (TextView) view.findViewById(R.id.offerTitle);
        tv.setText(offer.getTitle());

        return view;
    }
}
