package com.bargainhunter.bargainhunterandroid.models.DTOs.categories;

import java.util.Collection;
import java.util.HashSet;

public class CategoriesDTO {
    private Collection<CategoryDTO> categories;

    public CategoriesDTO() {
        categories = new HashSet<>();
    }

    public CategoriesDTO(Collection<CategoryDTO> categories) {
        this.categories = categories;
    }

    public Collection<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Collection<CategoryDTO> categories) {
        this.categories = categories;
    }
}
