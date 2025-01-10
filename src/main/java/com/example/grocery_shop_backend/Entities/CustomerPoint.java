package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_customer_point")
public class CustomerPoint
{
    @Id
    @Column(name = "customer_point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @Column(name = "customer_point")
    private String customerPoint;

    @Column(name = "customer_available_point")
    private String customerAvailablePoint;

    // 1 => Welcome Point
    // 2 => Used Point
    @Column(name = "customer_point_type")
    private String customerPointType;

    @Column(name = "customer_point_detail")
    private String customerPointDetail;

    @Column(name = "c_date")
    private String c_date;

    public CustomerPoint(){}

    public CustomerPoint(int id, Customer customer, String customerPoint, String customerAvailablePoint, String customerPointType, String customerPointDetail, String c_date) {
        this.id = id;
        this.customer = customer;
        this.customerPoint = customerPoint;
        this.customerAvailablePoint = customerAvailablePoint;
        this.customerPointType = customerPointType;
        this.customerPointDetail = customerPointDetail;
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

    public String getCustomerPoint() {
        return customerPoint;
    }

    public void setCustomerPoint(String customerPoint) {
        this.customerPoint = customerPoint;
    }

    public String getCustomerAvailablePoint() {
        return customerAvailablePoint;
    }

    public void setCustomerAvailablePoint(String customerAvailablePoint) {
        this.customerAvailablePoint = customerAvailablePoint;
    }

    public String getCustomerPointType() {
        return customerPointType;
    }

    public void setCustomerPointType(String customerPointType) {
        this.customerPointType = customerPointType;
    }

    public String getCustomerPointDetail() {
        return customerPointDetail;
    }

    public void setCustomerPointDetail(String customerPointDetail) {
        this.customerPointDetail = customerPointDetail;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }
}
