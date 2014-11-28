package com.bargainhunter.bargainhunterandroid.controllers.adapters;

import android.app.Activity;
import android.widget.Toast;
import com.bargainhunter.bargainhunterandroid.DAOs.OfferAPI;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Georgia on 11/26/14.
 */
public class OfferInfoController {
    Offer offer = null;

    public Offer requestData(String mEndpoint, String mOfferId, final Activity activity) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(mEndpoint)
                .build();

        //implement the api interface
        OfferAPI api = adapter.create(OfferAPI.class);

        //connect to server and user getOffer.
        api.getOffer(Long.valueOf(mOfferId).longValue() , new Callback<Offer>() {

            //Here i can save my data if the connection was successful.
            @Override
            public void success(Offer arg0, Response response) {
                offer = arg0;
            }

            //Here i can handle the Retrofit error. Connection unsuccessful.
            @Override
            public void failure(RetrofitError arg0) {
                offer = null;
                Toast.makeText(activity, arg0.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return offer;
    }
}
