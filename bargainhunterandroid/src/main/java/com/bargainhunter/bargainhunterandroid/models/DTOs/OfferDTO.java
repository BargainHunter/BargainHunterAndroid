package com.bargainhunter.bargainhunterandroid.models.DTOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class OfferDTO implements Serializable {
    private Long offerId;
    private String title;
    private String description;
    private Double price;
    private Double oldPrice;
    private Collection<OfferSubcategoryDTO> subcategories;

    public OfferDTO() {
        subcategories = new ArrayList<>();
    }

    public OfferDTO(Long offerId, String title, String description, Double price, Double oldPrice, Collection<OfferSubcategoryDTO> subcategories) {
        this.offerId = offerId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.oldPrice = oldPrice;
        this.subcategories = subcategories;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Collection<OfferSubcategoryDTO> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Collection<OfferSubcategoryDTO> subcategories) {
        this.subcategories = subcategories;
    }
}
