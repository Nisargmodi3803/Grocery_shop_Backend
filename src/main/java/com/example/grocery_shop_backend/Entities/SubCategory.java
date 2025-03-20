package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_subcategory")
public class SubCategory
{
   @Id
   @Column(name = "subcategory_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

    //Product Category ID
    //Many to One Mapping with Category Table
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "subcategory_cat_id")
    private Category category;

   @Column(name = "subcategory_name")
   private String name;

   @Column(name = "subcategory_image")
   private String image_url;

   @Column(name = "subcategory_description")
   private String description;

   @Column(name = "subcategory_priority")
   private int priority;

   @Column(name = "slug_title")
   private String slug_title;

   @Column(name = "is_deleted")
   private int isDeleted; // 1 => Not Delete & 2 => Delete

   @Column(name = "c_date")
   private String c_date;

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
