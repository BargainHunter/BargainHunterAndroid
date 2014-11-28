package com.bargainhunter.bargainhunterandroid.DAOs;

import com.bargainhunter.bargainhunterandroid.models.entities.Store;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

/**
 * Must be implemented in Activities or Fragments to retrieve JSON data from ENDPOINT + @GET parameter.
 * (ENDPOINT must be public static final String representing
 * the root domain of the Web Service location.)
 *
 * Created by Tommy on 11/18/2014.
 */
public interface StoreAPI {

    //GET method for ENDPOINT/stores?latitude={}&longitude={}&radius={} (Asynchronous execution)
    @GET("/stores")
    public void getStores(@Query("latitude") double latitude,
                          @Query("longitude") double longitude,
                          @Query("radius") double radius,
                          Callback<List<Store>> response);

    //GET method for ENDPOINT/stores/storeId (Asynchronous execution)
    @GET("/stores/{storeId}")
    public void getStore(@Path("storeId") long storeId, Callback<Store> response);

}
