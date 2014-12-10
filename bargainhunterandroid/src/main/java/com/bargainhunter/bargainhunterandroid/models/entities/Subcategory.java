package com.bargainhunter.bargainhunterandroid.models.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "SUBCATEGORY")
public class Subcategory extends Model {
    @Column(name = "subcategory_id")
    private Long subcategoryId;
    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private Category category;

    public Subcategory() {
        super();
    }

    public Subcategory(Long subcategoryId, String description, Category category) {
        super();

        this.subcategoryId = subcategoryId;
        this.description = description;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
