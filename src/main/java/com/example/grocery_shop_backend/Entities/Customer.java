package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_customer")
public class Customer
{
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_mobile")
    private String customerMobile;

    @Column(name = "customer_image")
    private String customerImage;

    @Column(name = "customer_password")
    private String customerPassword;

    @Column(name = "customer_city")
    private String customerCity;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "customer_pincode")
    private String customerPincode;

    @Column(name = "customer_gender",nullable = true)
    private int customerGender; // 0 = not mention yet, 1=Male, 2=Female, 3=others

    @Column(name = "customer_dob")
    private String customerDob;

    @Column(name = "customer_otp")
    private String customerOtp;

    @Column(name = "customer_android_token")
    private String customerAndroidToken;

    @Column(name = "customer_ios_token")
    private String customerIosToken;

    @Column(name = "c_date")
    private String cDate;

    @Column(name = "customer_referral_code")
    private String customerReferralCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_referred_by")
    private Customer customerReferralBy;

    @Column(name = "customer_points")
    private double customerPoint;

    @Column(name = "is_deleted")
    private int isDeleted;

    public Customer(){}

    public Customer(int customerId, String customerName, String customerEmail, String customerMobile, String customerImage, String customerPassword, String customerCity, String customerAddress, String customerPincode, int customerGender, String customerDob, String customerOtp, String customerAndroidToken, String customerIosToken, String cDate, String customerReferralCode, Customer customerReferralBy, double customerPoint, int isDeleted) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerMobile = customerMobile;
        this.customerImage = customerImage;
        this.customerPassword = customerPassword;
        this.customerCity = customerCity;
        this.customerAddress = customerAddress;
        this.customerPincode = customerPincode;
        this.customerGender = customerGender;
        this.customerDob = customerDob;
        this.customerOtp = customerOtp;
        this.customerAndroidToken = customerAndroidToken;
        this.customerIosToken = customerIosToken;
        this.cDate = cDate;
        this.customerReferralCode = customerReferralCode;
        this.customerReferralBy = customerReferralBy;
        this.customerPoint = customerPoint;
        this.isDeleted = isDeleted;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPincode() {
        return customerPincode;
    }

    public void setCustomerPincode(String customerPincode) {
        this.customerPincode = customerPincode;
    }

    public int getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(int customerGender) {
        this.customerGender = customerGender;
    }

    public String getCustomerDob() {
        return customerDob;
    }

    public void setCustomerDob(String customerDob) {
        this.customerDob = customerDob;
    }

    public String getCustomerOtp() {
        return customerOtp;
    }

    public void setCustomerOtp(String customerOtp) {
        this.customerOtp = customerOtp;
    }

    public String getCustomerAndroidToken() {
        return customerAndroidToken;
    }

    public void setCustomerAndroidToken(String customerAndroidToken) {
        this.customerAndroidToken = customerAndroidToken;
    }

    public String getCustomerIosToken() {
        return customerIosToken;
    }

    public void setCustomerIosToken(String customerIosToken) {
        this.customerIosToken = customerIosToken;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public String getCustomerReferralCode() {
        return customerReferralCode;
    }

    public void setCustomerReferralCode(String customerReferralCode) {
        this.customerReferralCode = customerReferralCode;
    }

    public Customer getCustomerReferralBy() {
        return customerReferralBy;
    }

    public void setCustomerReferralBy(Customer customerReferralBy) {
        this.customerReferralBy = customerReferralBy;
    }

    public double getCustomerPoint() {
        return customerPoint;
    }

    public void setCustomerPoint(double customerPoint) {
        this.customerPoint = customerPoint;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
