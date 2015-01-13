package com.bargainhunter.bargainhunterandroid.models.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Veruz on 9/1/2015.
 */

@Table(name = "FAVORITE_STORES")
public class FavoriteStores extends Model {
    @Column(name = "store_id")
    private Long id;

    public FavoriteStores(){ super(); }

    public FavoriteStores(Long id){
        super();

        this.id = id;
    }

    public Long getStoreId() {
        return id;
    }

    public void setStoreId(Long id) {
        this.id = id;
    }
}

