package com.example.grocery_shop_backend.Dto;

import com.example.grocery_shop_backend.Entities.Customer;

public class ProductReviewsByProductDTO
{
    private Customer customer;
    private int rating;
    private String review;

    public ProductReviewsByProductDTO() {}

    public ProductReviewsByProductDTO(Customer customer, int rating, String review) {
        this.customer = customer;
        this.rating = rating;
        this.review = review;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
