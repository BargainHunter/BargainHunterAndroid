package com.bargainhunter.bargainhunterandroid.models.DTOs.categories;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public class CategoryDTO implements Serializable {
    private Long categoryId;
    private String description;

    private Collection<SubcategoryDTO> subcategories;

    public CategoryDTO() {
        subcategories = new HashSet<>();
    }

    public CategoryDTO(Long categoryId, String description, Collection<SubcategoryDTO> subcategories) {
        this.categoryId = categoryId;
        this.description = description;
        this.subcategories = subcategories;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<SubcategoryDTO> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Collection<SubcategoryDTO> subcategories) {
        this.subcategories = subcategories;
    }
}
