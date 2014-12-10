package com.bargainhunter.bargainhunterandroid.restAPIs;

import com.bargainhunter.bargainhunterandroid.models.DTOs.categories.CategoriesDTO;
import retrofit.Callback;
import retrofit.http.GET;

public interface ICategoryAPI {
    //GET method for ENDPOINT/search_in_radius?latitude={}&longitude={}&radius={} (Asynchronous execution)
    @GET("/categories")
    public void getAllCategories(Callback<CategoriesDTO> response);
}
