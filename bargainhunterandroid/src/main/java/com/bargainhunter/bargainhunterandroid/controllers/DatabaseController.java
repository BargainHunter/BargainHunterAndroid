package com.bargainhunter.bargainhunterandroid.controllers;

import android.content.Context;
import android.widget.Toast;
import com.bargainhunter.bargainhunterandroid.models.Coordinates;
import com.bargainhunter.bargainhunterandroid.models.DTOs.categories.CategoriesDTO;
import com.bargainhunter.bargainhunterandroid.models.DTOs.categories.CategoryDTO;
import com.bargainhunter.bargainhunterandroid.models.DTOs.categories.SubcategoryDTO;
import com.bargainhunter.bargainhunterandroid.models.DTOs.search.*;
import com.bargainhunter.bargainhunterandroid.models.entities.*;
import com.bargainhunter.bargainhunterandroid.restAPIs.ICategoryAPI;
import com.bargainhunter.bargainhunterandroid.restAPIs.ISearchAPI;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DatabaseController {
    private static final String mEndpoint = "http://bargainhunter.dyndns.org:8080/bargainhunterws";

    public static void updateDatabaseCategories(final Context context) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(mEndpoint)
                .build();

        ICategoryAPI api = adapter.create(ICategoryAPI.class);

        api.getAllCategories(new Callback<CategoriesDTO>() {
            @Override
            public void success(CategoriesDTO categoriesDTO, Response response) {
                for (CategoryDTO categoryDTO : categoriesDTO.getCategories()) {
                    Category category = new Category(
                            categoryDTO.getCategoryId(),
                            categoryDTO.getDescription()
                    );

                    category.save();

                    for (SubcategoryDTO subcategoryDTO : categoryDTO.getSubcategories()) {
                        Subcategory subcategory = new Subcategory(
                                subcategoryDTO.getSubcategoryId(),
                                subcategoryDTO.getDescription(),
                                category
                        );

                        subcategory.save();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void updateDatabase(final Context context) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(mEndpoint)
                .build();

        ISearchAPI api = adapter.create(ISearchAPI.class);

        Coordinates phoneLoc = new LocationController().findCoordinates(context);

        api.getAllBranchesWithStoresAndOffersInRadius(
                phoneLoc.getLatitude(),
                phoneLoc.getLongitude(),
                100D,
                new Callback<SearchInRadiusDTO>() {
                    @Override
                    public void success(SearchInRadiusDTO searchInRadiusDTO, Response response) {
                        for (BranchDTO branchDTO : searchInRadiusDTO.getBranches()) {
                            Branch branch = new Branch(
                                    branchDTO.getBranchId()
                            );

                            branch.save();

                            for (StoreDTO storeDTO : branchDTO.getStores()) {
                                Store store = new Store(
                                        storeDTO.getStoreId(),
                                        storeDTO.getStoreName(),
                                        storeDTO.getCity(),
                                        storeDTO.getAddress(),
                                        storeDTO.getAddressNo(),
                                        storeDTO.getLatitude(),
                                        storeDTO.getLongitude(),
                                        branch
                                );

                                store.save();
                            }

                            for (OfferDTO offerDTO : branchDTO.getOffers()) {
                                Offer offer = new Offer(
                                        offerDTO.getOfferId(),
                                        offerDTO.getTitle(),
                                        offerDTO.getDescription(),
                                        offerDTO.getPrice(),
                                        offerDTO.getOldPrice(),
                                        branch
                                );

                                offer.save();

                                for (OfferSubcategoryDTO offerSubcategoryDTO : offerDTO.getSubcategories()) {
                                    OfferSubcategory offerSubcategory = new OfferSubcategory(
                                            offerSubcategoryDTO.getSubcategoryId(),
                                            offer
                                    );

                                    offerSubcategory.save();
                                }
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
