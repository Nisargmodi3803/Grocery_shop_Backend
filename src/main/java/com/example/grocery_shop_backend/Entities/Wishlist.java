package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_wishlist")
public class Wishlist {
    @Id
    @Column(name = "wishlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "tbl_product", // Name of the join table
//            joinColumns = @JoinColumn(name = "wishlist_id"), // FK to Wishlist
//            inverseJoinColumns = @JoinColumn(name = "product_id") // FK to Product
//    )
//    private Set<Product> products = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id") // FK to Product
    private Product product;

    @Column(name = "c_date")
    String c_date;

    public Wishlist(int id, Customer customer, Product product, String c_date) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.c_date = c_date;
    }
    public Wishlist() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }
}