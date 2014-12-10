package com.bargainhunter.bargainhunterandroid.models.DTOs;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public class BranchDTO implements Serializable {
    private Long branchId;

    private Collection<StoreDTO> stores;
    private Collection<OfferDTO> offers;

    public BranchDTO() {
        stores = new HashSet<>();
        offers = new HashSet<>();
    }

    public BranchDTO(Long branchId, Collection<StoreDTO> stores, Collection<OfferDTO> offers) {
        this.branchId = branchId;
        this.stores = stores;
        this.offers = offers;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Collection<StoreDTO> getStores() {
        return stores;
    }

    public void setStores(Collection<StoreDTO> stores) {
        this.stores = stores;
    }

    public Collection<OfferDTO> getOffers() {
        return offers;
    }

    public void setOffers(Collection<OfferDTO> offers) {
        this.offers = offers;
    }
}
