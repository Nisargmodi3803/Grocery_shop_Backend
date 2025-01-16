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

    @ManyToOne
    @JoinColumn(name = "cart_customer_id")
    Customer customer;

    @ManyToMany
    @JoinTable(
            name = "tbl_product", // Name of the join table
            joinColumns = @JoinColumn(name = "cart_id"), // Foreign key column for Cart
            inverseJoinColumns = @JoinColumn(name = "product_id") // Foreign key column for Product
    )
    private Set<Product> products;

    @Column(name = "product_quantity")
    private int productQuantity;

    @Column(name = "c_date")
    private String c_date;

    public Cart(){}

    public Cart(int id, Customer customer, Set<Product> products, int productQuantity, String c_date) {
        this.id = id;
        this.customer = customer;
        this.products = products;
        this.productQuantity = productQuantity;
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
}
