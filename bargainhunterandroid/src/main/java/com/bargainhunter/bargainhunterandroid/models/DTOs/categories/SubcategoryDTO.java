package com.bargainhunter.bargainhunterandroid.models.DTOs.categories;

import java.io.Serializable;

public class SubcategoryDTO implements Serializable {
    private Long subcategoryId;
    private String description;

    public SubcategoryDTO() {}

    public SubcategoryDTO(Long subcategoryId, String description) {
        this.subcategoryId = subcategoryId;
        this.description = description;
    }

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
