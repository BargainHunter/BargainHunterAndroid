
package com.bargainhunter.bargainhunterandroid.controllers.adapters;


import java.util.List;

import android.app.Activity;
import android.widget.Toast;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.bargainhunter.bargainhunterandroid.DAOs.OfferAPI;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;



/**
 * Created by Georgia on 26/11/2014.
 */
public class OfferListController {

    List<Offer> offerList = null;

    public List<Offer> requestData(String mEndpoint, final Activity activity) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(mEndpoint)
                .build();

        //implement the api interface
        OfferAPI api = adapter.create(OfferAPI.class);

        //connect to server and user getOffer.
        api.getOffers(new Callback<List<Offer>>() {

            //Here i can save my data if the connection was successful.
            @Override
            public void success(List<Offer> offers, Response response) {offerList = offers;}

            //Here i can handle the Retrofit error. Connection unsuccessful.
            @Override
            public void failure(RetrofitError error) {
                offerList = null;
                Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
        return offerList;
    }
}