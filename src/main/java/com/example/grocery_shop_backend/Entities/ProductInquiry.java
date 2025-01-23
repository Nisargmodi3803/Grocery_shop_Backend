package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_product_inquiry")
public class ProductInquiry
{
    @Id
    @Column(name = "inquiry_id")
    private int inquiryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "inquiry_quantity")
    private int inquiryQuantity;

    @Column(name = "inquiry_message")
    private String inquiryMessage;

    @Column(name = "c_date")
    private String cDate;

    @Column(name = "is_deleted")
    private int isDeleted;

    public ProductInquiry() {}

    public ProductInquiry(int inquiryId, Customer customer, Product product, int inquiryQuantity, String inquiryMessage, String cDate, int isDeleted) {
        this.inquiryId = inquiryId;
        this.customer = customer;
        this.product = product;
        this.inquiryQuantity = inquiryQuantity;
        this.inquiryMessage = inquiryMessage;
        this.cDate = cDate;
        this.isDeleted = isDeleted;
    }

    public int getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getInquiryQuantity() {
        return inquiryQuantity;
    }

    public void setInquiryQuantity(int inquiryQuantity) {
        this.inquiryQuantity = inquiryQuantity;
    }

    public String getInquiryMessage() {
        return inquiryMessage;
    }

    public void setInquiryMessage(String inquiryMessage) {
        this.inquiryMessage = inquiryMessage;
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
