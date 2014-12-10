package com.bargainhunter.bargainhunterandroid.models.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "OFFER_SUBCATEGORY")
public class OfferSubcategory extends Model {
    @Column(name = "subcategory_id")
    private Long subcategoryId;

    @Column(name = "offer")
    private Offer offer;

    public OfferSubcategory() {
        super();
    }

    public OfferSubcategory(Long subcategoryId, Offer offer) {
        super();

        this.subcategoryId = subcategoryId;
        this.offer = offer;
    }

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
