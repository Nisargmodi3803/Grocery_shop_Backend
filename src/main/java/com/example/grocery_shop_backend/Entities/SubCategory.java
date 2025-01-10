package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_subcategory")
public class SubCategory
{
   @Id
   @Column(name = "subcategory_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int id;

    //Product Category ID
    //Many to One Mapping with Category Table
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "subcategory_cat_id")
   Category category;

   @Column(name = "subcategory_name")
   String name;

   @Column(name = "subcategory_image")
   String image_url;

   @Column(name = "subcategory_description")
   String description;

   @Column(name = "subcategory_priority")
   int priority;

   @Column(name = "slug_title")
   String slug_title;

   @Column(name = "is_deleted")
   int is_deleted;

   @Column(name = "c_date")
   String c_date;

   public SubCategory(){}

    public SubCategory(int id, Category category, String name, String image_url, String description, int priority, String slug_title, int is_deleted, String c_date) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.image_url = image_url;
        this.description = description;
        this.priority = priority;
        this.slug_title = slug_title;
        this.is_deleted = is_deleted;
        this.c_date = c_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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
