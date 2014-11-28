package com.bargainhunter.bargainhunterandroid.DAOs;

import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
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
public interface OfferAPI {

    //GET method for ENDPOINT/offers?latitude={}&longitude={}&radius={} (Asynchronous execution)
    @GET("/offers")
    public void getOffers(@Query("latitude") double latitude,
                          @Query("longitude") double longitude,
                          @Query("radius") double radius,
                          Callback<List<Offer>> response);

    //GET method for ENDPOINT/offers/offerId (Asynchronous execution)
    @GET("/offers/{offerId}")
    public void getOffer(@Path("offerId") long offerId, Callback<Offer> response);

    //GET method for ENDPOINT/stores/storeId/offers (Asynchronous execution)
    @GET("/stores/{storeId}/offers")
    public void getOffersFromStore(@Path("storeId") long storeId, Callback<List<Offer>> response);
}
