package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_marketing_tag")
public class Marketing
{
    @Id
    @Column(name = "marketing_tag_id")
    private int marketingTagId;

    @Column(name = "marketing_tag_title")
    private String marketingTagTitle;

    @Column(name = "marketing_tag_description")
    private String marketingTagDescription;

    @Column(name = "marketing_tag_image")
    private String marketingTagImage;

    @Column(name = "marketing_tag_priority")
    private int marketingTagPriority;

    @Column(name = "is_deleted")
    private int isDeleted; // 1 = Not Deleted & 2 = Deleted

    @Column(name = "c_date")
    private String cDate;

    public Marketing() {}

    public Marketing(int marketingTagId, String marketingTagTitle, String marketingTagDescription, String marketingTagImage, int marketingTagPriority, int isDeleted, String cDate) {
        this.marketingTagId = marketingTagId;
        this.marketingTagTitle = marketingTagTitle;
        this.marketingTagDescription = marketingTagDescription;
        this.marketingTagImage = marketingTagImage;
        this.marketingTagPriority = marketingTagPriority;
        this.isDeleted = isDeleted;
        this.cDate = cDate;
    }

    public int getMarketingTagId() {
        return marketingTagId;
    }

    public void setMarketingTagId(int marketingTagId) {
        this.marketingTagId = marketingTagId;
    }

    public String getMarketingTagTitle() {
        return marketingTagTitle;
    }

    public void setMarketingTagTitle(String marketingTagTitle) {
        this.marketingTagTitle = marketingTagTitle;
    }

    public String getMarketingTagDescription() {
        return marketingTagDescription;
    }

    public void setMarketingTagDescription(String marketingTagDescription) {
        this.marketingTagDescription = marketingTagDescription;
    }

    public String getMarketingTagImage() {
        return marketingTagImage;
    }

    public void setMarketingTagImage(String marketingTagImage) {
        this.marketingTagImage = marketingTagImage;
    }

    public int getMarketingTagPriority() {
        return marketingTagPriority;
    }

    public void setMarketingTagPriority(int marketingTagPriority) {
        this.marketingTagPriority = marketingTagPriority;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }
}
