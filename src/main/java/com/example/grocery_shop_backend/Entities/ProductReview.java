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

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "order_id")
//    private ;

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

}
