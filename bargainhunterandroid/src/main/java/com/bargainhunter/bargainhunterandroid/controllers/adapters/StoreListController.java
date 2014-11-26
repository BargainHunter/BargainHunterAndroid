package com.bargainhunter.bargainhunterandroid.controllers.adapters;

import android.app.Activity;
import android.widget.Toast;
import com.bargainhunter.bargainhunterandroid.DAOs.StoreAPI;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * Created by Stratos on 26/11/2014.
 */
public class StoreListController {
    List<Store> storeList = null;


    public List<Store> requestData( String mEndpoint, final Activity activity) {


        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(mEndpoint)
                .build();

        //implement the api interface
        StoreAPI api = adapter.create(StoreAPI.class);

        //connect to server and user getOffer.
        api.getStores(new Callback<List<Store>>() {

            //Here i can save my data if the connection was successful.
            @Override
            public void success(List<Store> stores, Response response) {
                storeList = stores;

            }

            //Here i can handle the Retrofit error. Connection unsuccessful.
            @Override
            public void failure(RetrofitError error) {
                storeList=null;
                Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return storeList;
    }
}
