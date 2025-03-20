package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_brand")
public class Brand
{
    @Id
    @Column(name = "brand_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "brand_name")
    private String name;

    @Column(name = "brand_image")
    private String image_url;

    @Column(name = "brand_description")
    private String description;

    @Column(name = "slug_title")
    private String slug_title;

    @Column(name = "is_deleted")
    private int isDeleted; // 1 => Not Delete & 2 => Delete

    @Column(name = "c_date")
    private String c_date;

    public Brand(){}

    public Brand(int id, String name, String image_url, String description, String slug_title, int isDeleted, String c_date) {
        this.id = id;
        this.name = name;
        this.image_url = image_url;
        this.description = description;
        this.slug_title = slug_title;
        this.isDeleted = isDeleted;
        this.c_date = c_date;
    }

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

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }
}
