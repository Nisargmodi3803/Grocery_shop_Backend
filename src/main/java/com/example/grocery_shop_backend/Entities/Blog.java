package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Entity
@Table(name = "tbl_blog")
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
public class Blog
{
    @Id
    @Column(name = "blog_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "blog_title")
    String title;

    @Column(name = "blog_image")
    String image_url;

    @Column(name = "blog_description")
    String description;

    @Column(name = "blog_long_description")
    String long_description;

    @Column(name = "blog_date")
    String date;
//    Date date;

    @Column(name = "blog_keywords")
    String keywords;

    @Column(name = "slug_title")
    String slug_title;

    // Below 4 is optional if don't need in future then remove from here and Database as well as.

    @Column(name = "seo_author")
    String seo_author;

    @Column(name = "seo_title")
    String seo_title;

    @Column(name = "c_date")
    String c_date;
//    Date c_date;

    @Column(name = "is_deleted")
    int is_deleted; // 1 => Not Delete & 2 => Delete

    public Blog(int id, String title, String image_url, String description, String long_description, String date, String keywords, String slug_title, String seo_author, String seo_title, String c_date, int is_deleted) {
        this.id = id;
        this.title = title;
        this.image_url = image_url;
        this.description = description;
        this.long_description = long_description;
        this.date = date;
        this.keywords = keywords;
        this.slug_title = slug_title;
        this.seo_author = seo_author;
        this.seo_title = seo_title;
        this.c_date = c_date;
        this.is_deleted = is_deleted;
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

    public String getSeo_author() {
        return seo_author;
    }

    public void setSeo_author(String seo_author) {
        this.seo_author = seo_author;
    }

    public String getSeo_title() {
        return seo_title;
    }

    public void setSeo_title(String seo_title) {
        this.seo_title = seo_title;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }
}
