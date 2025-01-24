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
    private double customerPoint;

    @Column(name = "customer_available_point")
    private double customerAvailablePoint;

    // 1 => In
    // 2 => Out
    @Column(name = "customer_point_type")
    private int customerPointType;

    @Column(name = "customer_point_detail")
    private String customerPointDetail;

    @Column(name = "c_date")
    private String c_date;

    @Column(name = "is_deleted")
    private int isDeleted;

    public CustomerPoint(){}

    public CustomerPoint(int id, Customer customer, double customerPoint, double customerAvailablePoint, int customerPointType, String customerPointDetail, String c_date, int isDeleted) {
        this.id = id;
        this.customer = customer;
        this.customerPoint = customerPoint;
        this.customerAvailablePoint = customerAvailablePoint;
        this.customerPointType = customerPointType;
        this.customerPointDetail = customerPointDetail;
        this.c_date = c_date;
        this.isDeleted = isDeleted;
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

    public double getCustomerPoint() {
        return customerPoint;
    }

    public void setCustomerPoint(double customerPoint) {
        this.customerPoint = customerPoint;
    }

    public double getCustomerAvailablePoint() {
        return customerAvailablePoint;
    }

    public void setCustomerAvailablePoint(double customerAvailablePoint) {
        this.customerAvailablePoint = customerAvailablePoint;
    }

    public int getCustomerPointType() {
        return customerPointType;
    }

    public void setCustomerPointType(int customerPointType) {
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

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
