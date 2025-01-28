package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;

@Entity
@Table(name = "tbl_product_review")
public class ProductReview
{
    @Id
    @Column(name = "rating_id")
    private int ratingId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "rating")
    private int rating;

    @Column(name = "review")
    private String review;

    @Column(name = "status")
    private int status; // 1=Pending,2=Approved,3=Rejected

    @Column(name = "is_deleted")
    private int isDeleted;

    @Column(name = "c_date")
    private String cDate;

    public ProductReview() {}

    public ProductReview(int ratingId, Customer customer, Invoice invoice, Product product, int rating, String review, int status, int isDeleted, String cDate) {
        this.ratingId = ratingId;
        this.customer = customer;
        this.invoice = invoice;
        this.product = product;
        this.rating = rating;
        this.review = review;
        this.status = status;
        this.isDeleted = isDeleted;
        this.cDate = cDate;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
