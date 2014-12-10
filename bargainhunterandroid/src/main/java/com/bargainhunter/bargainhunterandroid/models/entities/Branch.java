package com.bargainhunter.bargainhunterandroid.models.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "BRANCH")
public class Branch extends Model {
    @Column(name = "branch_id")
    private Long branchId;

    public Branch() {
        super();
    }

    public Branch(Long branchId) {
        super();

        this.branchId = branchId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public List<Store> getStores() {
        return getMany(Store.class, "branch");
    }

    public List<Offer> getOffers() {
        return getMany(Offer.class, "branch");
    }
}
