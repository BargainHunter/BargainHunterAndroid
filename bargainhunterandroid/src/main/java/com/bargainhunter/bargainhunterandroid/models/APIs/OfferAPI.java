package com.bargainhunter.bargainhunterandroid.models.APIs;

import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

/**
 * Must be implemented in Activities or Fragments to retrieve JSON data from ENDPOINT + @GET parameter.
 * (ENDPOINT must be public static final String representing
 * the root domain of the Web Service location.)
 *
 * Created by Tommy on 11/18/2014.
 */
public interface OfferAPI {

    //GET method for ENDPOINT/offers (Asynchronous execution)
    @GET("/offers")
    public void getOffers(Callback<List<Offer>> response);

    //GET method for ENDPOINT/offers/offerId (Asynchronous execution)
    @GET("/offers/{offerId}")
    public void getOffer(@Path("offerId") long offerId, Callback<Offer> response);

}
