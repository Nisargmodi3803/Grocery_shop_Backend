package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_slug")
public class Slug
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slug_id")
    private int slugId;

    @Column(name = "slug_title")
    private String slugTitle;

    @Column(name = "slug_page")
    private String slugPage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "slug_item_id")
    private SubCategory subCategory;

    public Slug(){}

    public Slug(int slugId, String slugTitle, String slugPage, SubCategory subCategory) {
        this.slugId = slugId;
        this.slugTitle = slugTitle;
        this.slugPage = slugPage;
        this.subCategory = subCategory;
    }

    public int getSlugId() {
        return slugId;
    }

    public void setSlugId(int slugId) {
        this.slugId = slugId;
    }

    public String getSlugTitle() {
        return slugTitle;
    }

    public void setSlugTitle(String slugTitle) {
        this.slugTitle = slugTitle;
    }

    public String getSlugPage() {
        return slugPage;
    }

    public void setSlugPage(String slugPage) {
        this.slugPage = slugPage;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
}
