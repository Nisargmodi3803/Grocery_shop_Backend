package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tbl_wishlist")
public class Wishlist
{
    @Id
    @Column(name = "wishlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToMany
    @JoinColumn(name = "product_id")
    Set<Product> products;

    @Column(name = "c_date")
    String c_date;

    public Wishlist(){}

    public Wishlist(int id, Customer customer, Set<Product> products, String c_date) {
        this.id = id;
        this.customer = customer;
        this.products = products;
        this.c_date = c_date;
    }

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }
}
