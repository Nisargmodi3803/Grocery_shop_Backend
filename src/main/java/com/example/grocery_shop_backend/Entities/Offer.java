package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Entity
@Table(name = "tbl_offer")
public class Offer {

    @Id
    @Column(name = "offer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int offerId;

    @Column(name = "create_banner_for")
    private int createBannerFor; // 1=Brand, 2=Category, 3=Subcategory, 4=Selected Products

    @Column(name = "offer_title")
    private String offerTitle;

    @Column(name = "offer_description")
    private String offerDescription;

    @Column(name = "offer_image")
    private String offerImage;

    @Column(name = "offer_image_web")
    private String offerImageWeb;

    @Column(name = "offer_start_date")
    private String offerStartDate;

    @Column(name = "offer_end_date")
    private String offerEndDate;

    @Column(name = "offer_is_in_banner")
    private int offerIsInBanner;

    @Column(name = "slug_title")
    private String slugTitle;

    @Column(name = "c_date")
    private String cDate;

    @Column(name = "is_deleted")
    private int isDeleted; // 1 = Not Deleted & 2 = Deleted

    public Offer() {}

    public Offer(int offerId, int createBannerFor, String offerTitle, String offerDescription, String offerImage, String offerImageWeb, String offerStartDate, String offerEndDate, int offerIsInBanner, String slugTitle, String cDate, int isDeleted) {
        this.offerId = offerId;
        this.createBannerFor = createBannerFor;
        this.offerTitle = offerTitle;
        this.offerDescription = offerDescription;
        this.offerImage = offerImage;
        this.offerImageWeb = offerImageWeb;
        this.offerStartDate = offerStartDate;
        this.offerEndDate = offerEndDate;
        this.offerIsInBanner = offerIsInBanner;
        this.slugTitle = slugTitle;
        this.cDate = cDate;
        this.isDeleted = isDeleted;
    }


    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getCreateBannerFor() {
        return createBannerFor;
    }

    public void setCreateBannerFor(int createBannerFor) {
        this.createBannerFor = createBannerFor;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public String getOfferImageWeb() {
        return offerImageWeb;
    }

    public void setOfferImageWeb(String offerImageWeb) {
        this.offerImageWeb = offerImageWeb;
    }

    public String getOfferStartDate() {
        return offerStartDate;
    }

    public void setOfferStartDate(String offerStartDate) {
        this.offerStartDate = offerStartDate;
    }

    public String getOfferEndDate() {
        return offerEndDate;
    }

    public void setOfferEndDate(String offerEndDate) {
        this.offerEndDate = offerEndDate;
    }

    public int getOfferIsInBanner() {
        return offerIsInBanner;
    }

    public void setOfferIsInBanner(int offerIsInBanner) {
        this.offerIsInBanner = offerIsInBanner;
    }

    public String getSlugTitle() {
        return slugTitle;
    }

    public void setSlugTitle(String slugTitle) {
        this.slugTitle = slugTitle;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
