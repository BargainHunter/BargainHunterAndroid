package com.bargainhunter.bargainhunterandroid.models.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "OFFER")
public class Offer extends Model {
    @Column(name = "offer_id")
    public Long offerId;
    @Column(name = "title")
    public String title;
    @Column(name = "description")
    public String description;
    @Column(name = "price")
    public Double price;
    @Column(name = "old_price")
    public Double oldPrice;
    @Column(name = "branch")
    public Branch branch;

    public Offer() {
        super();
    }

    public Offer(Long offerId, String title, String description, Double price, Double oldPrice, Branch branch) {
        super();

        this.offerId = offerId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.oldPrice = oldPrice;
        this.branch = branch;
    }

    public List<OfferSubcategory> subcategories() {
        return getMany(OfferSubcategory.class, "offer");
    }
}
