package com.bargainhunter.bargainhunterandroid.controllers;

import android.content.Context;
import android.widget.Toast;
import com.bargainhunter.bargainhunterandroid.models.Coordinates;
import com.bargainhunter.bargainhunterandroid.models.DTOs.*;
import com.bargainhunter.bargainhunterandroid.models.entities.Branch;
import com.bargainhunter.bargainhunterandroid.models.entities.Offer;
import com.bargainhunter.bargainhunterandroid.models.entities.OfferSubcategory;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;
import com.bargainhunter.bargainhunterandroid.restAPIs.ISearchAPI;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DatabaseController {
    private static final String mEndpoint = "http://a4i.dyndns.org:8080/";

    public static void updateDatabase(final Context context) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(mEndpoint)
                .build();

        //implement the api interface
        ISearchAPI api = adapter.create(ISearchAPI.class);

        Coordinates phoneLoc = new LocationController().findCoordinates(context);

        //connect to server and user getOffer.
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
                    public void failure(RetrofitError arg0) {
                        Toast.makeText(context, arg0.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
