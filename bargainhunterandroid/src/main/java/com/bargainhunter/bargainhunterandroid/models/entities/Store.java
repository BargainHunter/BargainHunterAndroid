package com.bargainhunter.bargainhunterandroid.models.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "STORE")
public class Store extends Model {
    @Column(name = "store_id")
    public Long storeId;
    @Column(name = "store_name")
    public String storeName;
    @Column(name = "city")
    public String city;
    @Column(name = "address")
    public String address;
    @Column(name = "address_no")
    public String addressNo;
    @Column(name = "latitude")
    public Double latitude;
    @Column(name = "longitude")
    public Double longitude;
    @Column(name = "branch")
    public Branch branch;

    public Store() {
        super();
    }

    public Store(Long storeId, String storeName, String city, String address, String addressNo, Double latitude, Double longitude, Branch branch) {
        super();

        this.storeId = storeId;
        this.storeName = storeName;
        this.city = city;
        this.address = address;
        this.addressNo = addressNo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.branch = branch;
    }
}
