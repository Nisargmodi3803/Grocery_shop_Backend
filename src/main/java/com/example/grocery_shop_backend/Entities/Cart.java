package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tbl_cart")
public class Cart
{
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_product_id") // FK to Product
    private Product product;

    @Column(name = "product_quantity")
    private int productQuantity;

    @Column(name = "c_date")
    private String c_date;

    @Column(name = "is_deleted")
    int is_deleted; // 1 => Not Delete & 2 => Delete

    public Cart(){}

    public Cart(int id, Customer customer, Product product, int productQuantity, String c_date,int is_deleted) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.productQuantity = productQuantity;
        this.c_date = c_date;
        this.is_deleted = is_deleted;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }
}
