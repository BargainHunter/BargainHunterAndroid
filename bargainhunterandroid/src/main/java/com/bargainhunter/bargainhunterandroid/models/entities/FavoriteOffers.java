package com.bargainhunter.bargainhunterandroid.models.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Veruz on 9/1/2015.
 */

@Table(name = "FAVORITE_OFFERS")
public class FavoriteOffers extends Model {
    @Column(name = "offer_id")
    private Long offer_id;

    public FavoriteOffers(){ super(); }

    public FavoriteOffers(Long offer_id){
        super();
        this.offer_id = offer_id;
    }

    public Long getOfferId() {
        return offer_id;
    }



    public void setOfferId(Long offer_id) {
        this.offer_id = offer_id;
    }
}
