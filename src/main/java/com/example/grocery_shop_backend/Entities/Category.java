package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_category")
public class Category
{
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "category_name")
    String name;

    @Column(name = "category_image")
    String image_url;

    @Column(name = "category_description")
    String description;

    @Column(name = "slug_title")
    String slug_title;

    @Column(name = "is_deleted")
    int is_deleted; // 1 => Not Delete & 2 => Delete

    @Column(name = "c_date")
    String c_date;

    public Category(int id, String name, String image_url, String description, String slug_title, int is_deleted, String c_date) {
        this.id = id;
        this.name = name;
        this.image_url = image_url;
        this.description = description;
        this.slug_title = slug_title;
        this.is_deleted = is_deleted;
        this.c_date = c_date;
    }

    public Category(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug_title() {
        return slug_title;
    }

    public void setSlug_title(String slug_title) {
        this.slug_title = slug_title;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }
}
