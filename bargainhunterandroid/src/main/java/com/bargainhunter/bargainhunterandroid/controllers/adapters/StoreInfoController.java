package com.bargainhunter.bargainhunterandroid.controllers.adapters;

import android.app.Activity;
import android.widget.Toast;
import com.bargainhunter.bargainhunterandroid.DAOs.StoreAPI;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Stratos on 26/11/2014.
 */
public class StoreInfoController {
    Store store = null;

    public Store requestData(String mEndpoint, String mOfferId, final Activity activity) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(mEndpoint)
                .build();

        //implement the api interface
        StoreAPI api = adapter.create(StoreAPI.class);

        //connect to server and user getOffer.
        api.getStore(Long.valueOf(mOfferId).longValue() , new Callback<Store>() {

            //Here i can save my data if the connection was successful.
            @Override
            public void success(Store arg0, Response response) {
                store = arg0;
            }

            //Here i can handle the Retrofit error. Connection unsuccessful.
            @Override
            public void failure(RetrofitError arg0) {
                store = null;
                Toast.makeText(activity, arg0.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return store;
    }
}