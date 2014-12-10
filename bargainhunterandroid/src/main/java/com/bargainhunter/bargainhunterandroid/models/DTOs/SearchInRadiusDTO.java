package com.bargainhunter.bargainhunterandroid.models.DTOs;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SearchInRadiusDTO implements Serializable {
    private Double latitude;
    private Double longitude;
    private Double radius;

    private Set<BranchDTO> branches;

    public SearchInRadiusDTO() {
        branches = new HashSet<>();
    }

    public SearchInRadiusDTO(Double latitude, Double longitude, Double radius, Set<BranchDTO> branches) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.branches = branches;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Set<BranchDTO> getBranches() {
        return branches;
    }

    public void setBranches(Set<BranchDTO> branches) {
        this.branches = branches;
    }
}
