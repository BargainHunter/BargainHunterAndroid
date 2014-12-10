package com.bargainhunter.bargainhunterandroid.models.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "CATEGORY")
public class Category extends Model {
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "description")
    private String description;

    public Category() {
        super();
    }

    public Category(Long categoryId, String description) {
        super();

        this.categoryId = categoryId;
        this.description = description;
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

    public List<Subcategory> getSubcategories() {
        return getMany(Subcategory.class, "category");
    }
}
