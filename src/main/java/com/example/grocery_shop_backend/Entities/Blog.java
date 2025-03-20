package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Entity
@Table(name = "tbl_blog")
public class Blog
{
    @Id
    @Column(name = "blog_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "blog_title")
    private String title;

    @Column(name = "blog_image")
    private String image_url;

    @Column(name = "blog_description")
    private String description;

    @Column(name = "blog_long_description")
    private String long_description;

    @Column(name = "blog_date")
    private String date;

    @Column(name = "blog_keywords")
    private String keywords;

    @Column(name = "slug_title")
    private String slug_title;

    @Column(name = "c_date")
    private String c_date;

    @Column(name = "is_deleted")
    private int isDeleted; // 1 => Not Delete & 2 => Delete

    public Blog(int id, String title, String image_url, String description, String long_description, String date, String keywords, String slug_title, String c_date, int is_deleted) {
        this.id = id;
        this.title = title;
        this.image_url = image_url;
        this.description = description;
        this.long_description = long_description;
        this.date = date;
        this.keywords = keywords;
        this.slug_title = slug_title;
        this.c_date = c_date;
        this.isDeleted = is_deleted;
    }

    public Blog(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSlug_title() {
        return slug_title;
    }

    public void setSlug_title(String slug_title) {
        this.slug_title = slug_title;
    }


    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }

    public int getIs_deleted() {
        return isDeleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.isDeleted = is_deleted;
    }
}
