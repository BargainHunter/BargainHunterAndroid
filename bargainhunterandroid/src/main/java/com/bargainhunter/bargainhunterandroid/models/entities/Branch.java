package com.bargainhunter.bargainhunterandroid.models.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "BRANCH")
public class Branch extends Model {
    @Column(name = "branch_id")
    public Long branchId;

    public Branch() {
        super();
    }

    public Branch(Long branchId) {
        super();

        this.branchId = branchId;
    }

    public List<Store> stores() {
        return getMany(Store.class, "branch");
    }

    public List<Offer> offers() {
        return getMany(Offer.class, "branch");
    }
}
