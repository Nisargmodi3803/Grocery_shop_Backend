package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_coupon_code")
public class CouponCode
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private int couponId;

    @Column(name = "coupon_code")
    private String couponCode;

    @Column(name = "coupon_title")
    private String couponTitle;

    @Column(name = "coupon_start_date")
    private String couponStartDate;

    @Column(name = "coupon_end_date")
    private String couponEndDate;

    @Column(name = "coupon_type")
    private int couponType;

    @Column(name = "coupon_code_for")
    private int couponCodeFor;

    @Column(name = "coupon_value")
    private double couponValue;

    @Column(name = "coupon_minimum_bill_amount")
    private double couponMinimumBillAmount;

    @Column(name = "coupon_max_discount")
    private double couponMaxDiscount;

    @Column(name = "coupon_status")
    private int couponStatus;

    @Column(name = "is_deleted")
    private int isDeleted;// 1 => Not deleted & 2 => deleted

    @Column(name = "c_date")
    private String cDate;

    public CouponCode() {}

    public CouponCode(int couponId, String couponCode, String couponTitle, String couponStartDate, String couponEndDate, int couponType, int couponCodeFor, double couponValue, double couponMinimumBillAmount, double couponMaxDiscount, int couponStatus, int isDeleted, String cDate) {
        this.couponId = couponId;
        this.couponCode = couponCode;
        this.couponTitle = couponTitle;
        this.couponStartDate = couponStartDate;
        this.couponEndDate = couponEndDate;
        this.couponType = couponType;
        this.couponCodeFor = couponCodeFor;
        this.couponValue = couponValue;
        this.couponMinimumBillAmount = couponMinimumBillAmount;
        this.couponMaxDiscount = couponMaxDiscount;
        this.couponStatus = couponStatus;
        this.isDeleted = isDeleted;
        this.cDate = cDate;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getCouponStartDate() {
        return couponStartDate;
    }

    public void setCouponStartDate(String couponStartDate) {
        this.couponStartDate = couponStartDate;
    }

    public String getCouponEndDate() {
        return couponEndDate;
    }

    public void setCouponEndDate(String couponEndDate) {
        this.couponEndDate = couponEndDate;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public int getCouponCodeFor() {
        return couponCodeFor;
    }

    public void setCouponCodeFor(int couponCodeFor) {
        this.couponCodeFor = couponCodeFor;
    }

    public double getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(double couponValue) {
        this.couponValue = couponValue;
    }

    public double getCouponMinimumBillAmount() {
        return couponMinimumBillAmount;
    }

    public void setCouponMinimumBillAmount(double couponMinimumBillAmount) {
        this.couponMinimumBillAmount = couponMinimumBillAmount;
    }

    public double getCouponMaxDiscount() {
        return couponMaxDiscount;
    }

    public void setCouponMaxDiscount(double couponMaxDiscount) {
        this.couponMaxDiscount = couponMaxDiscount;
    }

    public int getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(int couponStatus) {
        this.couponStatus = couponStatus;
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
