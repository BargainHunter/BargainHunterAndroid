package com.bargainhunter.bargainhunterandroid.models.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Veruz on 9/1/2015.
 */

@Table(name = "FavOffers")
public class FavOffers extends Model {
    @Column(name = "offer_id")
    private Long id;

    public FavOffers(){ super(); }

    public FavOffers(Long id){
        super();

        this.id = id;
    }

    public Long getOfferId() {
        return id;
    }

    public void setOfferId(Long id) {
        this.id = id;
    }
}
