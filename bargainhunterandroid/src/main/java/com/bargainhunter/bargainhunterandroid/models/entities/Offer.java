package com.bargainhunter.bargainhunterandroid.models.entities;

/**
 * A simple POJO representing an Offer
 *
 * Created by Tommy on 11/18/2014.
 */
public class Offer {

    private long offerId;
    private String title;
    private String description;
    private double price;
    private Long companyId;

    public long getOfferId() {
        return offerId;
    }

    public void setOfferId(long offerId) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
