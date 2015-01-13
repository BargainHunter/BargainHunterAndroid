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
    private Long store_id;

    public FavoriteStores(){ super(); }

    public FavoriteStores(Long store_id){
        super();

        this.store_id = store_id;
    }

    public Long getStoreId() {
        return store_id;
    }

    public void setStoreId(Long store_id) {
        this.store_id = store_id;
    }
}

