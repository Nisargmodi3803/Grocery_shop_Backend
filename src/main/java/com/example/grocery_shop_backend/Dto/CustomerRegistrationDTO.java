package com.example.grocery_shop_backend.Dto;

public class CustomerRegistrationDTO {
    private String customerName;
    private String customerMobile;
    private String customerPassword;
    private String customerOtp;
    private String referralCode;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerOtp() {
        return customerOtp;
    }

    public void setCustomerOtp(String customerOtp) {
        this.customerOtp = customerOtp;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }
}
