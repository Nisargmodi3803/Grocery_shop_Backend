package com.example.grocery_shop_backend.Dto;

public class BlogDTO
{
    private String title;
    private String image;
    private String description;
    private String keywords;
    private String slugTitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSlugTitle() {
        return slugTitle;
    }

    public void setSlugTitle(String slugTitle) {
        this.slugTitle = slugTitle;
    }
}
