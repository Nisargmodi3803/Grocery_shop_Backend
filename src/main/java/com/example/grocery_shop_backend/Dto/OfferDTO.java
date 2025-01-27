package com.example.grocery_shop_backend.Dto;

public class OfferDTO
{
    private int banner; // 1=Brand, 2=Category, 3=Subcategory, 4=Selected Products
    private String title;
    private String description;
    private String image;
    private String imageWeb;
    private String startDate;
    private String endDate;
    private String slugTitle;

    public int getBanner_for() {
        return banner;
    }

    public void setBanner_for(int banner_for) {
        this.banner = banner_for;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_web() {
        return imageWeb;
    }

    public void setImage_web(String image_web) {
        this.imageWeb = image_web;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSlugTitle() {
        return slugTitle;
    }

    public void setSlugTitle(String slugTitle) {
        this.slugTitle = slugTitle;
    }
}
