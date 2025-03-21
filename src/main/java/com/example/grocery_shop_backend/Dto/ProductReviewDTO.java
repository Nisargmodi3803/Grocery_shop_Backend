package com.example.grocery_shop_backend.Dto;

import com.example.grocery_shop_backend.Entities.Customer;

public class ProductReviewDTO
{
    private String customerEmail;
    private int productId;
    private int invoiceId;
    private int rating;
    private String review;

    public ProductReviewDTO() {}

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
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
}
