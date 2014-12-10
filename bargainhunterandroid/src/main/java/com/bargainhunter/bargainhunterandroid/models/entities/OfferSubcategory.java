package com.bargainhunter.bargainhunterandroid.models.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "subcategory")
public class OfferSubcategory extends Model {
    @Column(name = "subcategory_id")
    public Long subcategoryId;
    @Column(name = "offer")
    public Offer offer;

    public OfferSubcategory() {
        super();
    }

    public OfferSubcategory(Long subcategoryId, Offer offer) {
        super();

        this.subcategoryId = subcategoryId;
        this.offer = offer;
    }
}
