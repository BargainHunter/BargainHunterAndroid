package com.bargainhunter.bargainhunterandroid.restAPIs;

import com.bargainhunter.bargainhunterandroid.models.DTOs.search.SearchInRadiusDTO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

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
