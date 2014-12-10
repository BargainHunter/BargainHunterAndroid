package com.bargainhunter.bargainhunterandroid.restAPIs;

import com.bargainhunter.bargainhunterandroid.models.DTOs.SearchInRadiusDTO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Must be implemented in Activities or Fragments to retrieve JSON data from ENDPOINT + @GET parameter.
 * (ENDPOINT must be public static final String representing
 * the root domain of the Web Service location.)
 * <p/>
 * Created by Tommy on 11/18/2014.
 */
public interface ISearchAPI {
    //GET method for ENDPOINT/search_in_radius?latitude={}&longitude={}&radius={} (Asynchronous execution)
    @GET("/search_in_radius")
    public void getAllBranchesWithStoresAndOffersInRadius(
            @Query("latitude") Double latitude,
            @Query("longitude") Double longitude,
            @Query("radius") Double radius,
            Callback<SearchInRadiusDTO> response
    );
}
