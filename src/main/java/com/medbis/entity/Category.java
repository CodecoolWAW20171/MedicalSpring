package com.medbis.entity;


import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "categories", schema = "public")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "name_category")
    @NotEmpty
    private String name;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "vCategory{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}

