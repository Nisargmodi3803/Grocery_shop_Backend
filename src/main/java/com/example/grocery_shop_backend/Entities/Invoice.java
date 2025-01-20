package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

import javax.naming.Name;

@Entity
@Table(name = "tbl_invoice")
public class Invoice
{
    @Id
    @Column(name = "invoice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoiceId;

    @Column(name = "invoice_num",unique = true)
    private String invoiceNum;

    @Column(name = "invoice_prefix")
    private String invoicePrefix;

    @Column(name = "invoice_date")
    private String invoiceDate;

    @Column(name = "invoice_time")
    private String invoiceTime;

    @Column(name = "invoice_financial_year")
    private String invoiceFinancialYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_customer_id")
    private Customer customer;

    @Column(name = "invoice_mobile")
    private String invoiceMobile;

    @Column(name = "invoice_name")
    private String invoiceName;

    @Column(name = "invoice_email_id")
    private String invoiceEmailId;

    @Column(name = "invoice_city")
    private String invoiceCity;

    @Column(name = "invoice_address")
    private String invoiceAddress;

    @Column(name = "invoice_total_amount")
    private double invoiceTotalAmount;

    @Column(name = "invoice_payable")
    private double invoicePayable;

    @Column(name = "invoice_remaining_amount")
    private double invoiceRemainingAmount;

    @Column(name = "invoice_received_amount")
    private double invoiceReceivedAmount;

    @Column(name = "invoice_pincode")
    private String invoicePincode;

    @Column(name = "invoice_delivery_charges")
    private String invoiceDeliveryCharges;

    @Column(name = "invoice_discount")
    private double invoiceDiscount;

    @Column(name = "invoice_coupon_code_discount")
    private double invoiceCouponCodeDiscount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_coupon_id")
    private CouponCode couponCode;

    @Column(name = "invoice_payment_mode")
    private int invoicePaymentMode; // 1 => COD & 2 => Online

    @Column(name = "invoice_generated_by")
    private String invoiceGeneratedBy;

    @Column(name = "invoice_updated_by")
    private int invoiceUpdatedBy;

    @Column(name = "invoice_updated_date")
    private String invoiceUpdatedDate;

    @Column(name = "invoice_delivery_date")
    private String invoiceDeliveryDate;

    @Column(name = "invoice_special_instruction")
    private String invoiceSpecialInstruction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_delivery_boy_id")
    private DeliveryBoy deliveryBoy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_delivery_time_slot_id")
    private DeliveryTimeSlot deliveryTimeSlot;

    // To hold the payment
    @Column(name = "invoice_is_hold")
    private String invoiceIsHold; // 1 => YES(to hold) & 2 => NO(to hold) [Generally 2]

    @Column(name = "invoice_status")
    private int invoiceStatus;//1 => Pending, 2 => Confirm, 3 => Dispatched, 4 => Delivered, 5 => Rejected, 6 => Canceled

    @Column(name = "invoice_razorpayOrderId")
    private String invoiceRazorpayOrderId;

    @Column(name = "invoice_razorpay_status")
    private int invoiceRazorpayStatus; // 1 => Pending, 2 => Success, 3 => Failure.

    @Column(name = "invoice_razorpay_payment_id")
    private String invoiceRazorpayPaymentId;

    @Column(name = "invoice_razorpay_signature")
    private String invoiceRazorpaySignature;

    @Column(name = "is_deleted")
    private boolean isDeleted; // 1 => Not deleted & 2 => Deleted

    @Column(name = "c_date")
    private String cDate;

    public Invoice(){}

    public Invoice(int invoiceId, String invoiceNum, String invoicePrefix, String invoiceDate, String invoiceTime, String invoiceFinancialYear, Customer customer, String invoiceMobile, String invoiceName, String invoiceEmailId, String invoiceCity, String invoiceAddress, double invoiceTotalAmount, double invoicePayable, double invoiceRemainingAmount, double invoiceReceivedAmount, String invoicePincode, String invoiceDeliveryCharges, double invoiceDiscount, double invoiceCouponCodeDiscount, CouponCode couponCode, int invoicePaymentMode, String invoiceGeneratedBy, int invoiceUpdatedBy, String invoiceUpdatedDate, String invoiceDeliveryDate, String invoiceSpecialInstruction, DeliveryBoy deliveryBoy, DeliveryTimeSlot deliveryTimeSlot, String invoiceIsHold, int invoiceStatus, String invoiceRazorpayOrderId, int invoiceRazorpayStatus, String invoiceRazorpayPaymentId, String invoiceRazorpaySignature, boolean isDeleted, String cDate) {
        this.invoiceId = invoiceId;
        this.invoiceNum = invoiceNum;
        this.invoicePrefix = invoicePrefix;
        this.invoiceDate = invoiceDate;
        this.invoiceTime = invoiceTime;
        this.invoiceFinancialYear = invoiceFinancialYear;
        this.customer = customer;
        this.invoiceMobile = invoiceMobile;
        this.invoiceName = invoiceName;
        this.invoiceEmailId = invoiceEmailId;
        this.invoiceCity = invoiceCity;
        this.invoiceAddress = invoiceAddress;
        this.invoiceTotalAmount = invoiceTotalAmount;
        this.invoicePayable = invoicePayable;
        this.invoiceRemainingAmount = invoiceRemainingAmount;
        this.invoiceReceivedAmount = invoiceReceivedAmount;
        this.invoicePincode = invoicePincode;
        this.invoiceDeliveryCharges = invoiceDeliveryCharges;
        this.invoiceDiscount = invoiceDiscount;
        this.invoiceCouponCodeDiscount = invoiceCouponCodeDiscount;
        this.couponCode = couponCode;
        this.invoicePaymentMode = invoicePaymentMode;
        this.invoiceGeneratedBy = invoiceGeneratedBy;
        this.invoiceUpdatedBy = invoiceUpdatedBy;
        this.invoiceUpdatedDate = invoiceUpdatedDate;
        this.invoiceDeliveryDate = invoiceDeliveryDate;
        this.invoiceSpecialInstruction = invoiceSpecialInstruction;
        this.deliveryBoy = deliveryBoy;
        this.deliveryTimeSlot = deliveryTimeSlot;
        this.invoiceIsHold = invoiceIsHold;
        this.invoiceStatus = invoiceStatus;
        this.invoiceRazorpayOrderId = invoiceRazorpayOrderId;
        this.invoiceRazorpayStatus = invoiceRazorpayStatus;
        this.invoiceRazorpayPaymentId = invoiceRazorpayPaymentId;
        this.invoiceRazorpaySignature = invoiceRazorpaySignature;
        this.isDeleted = isDeleted;
        this.cDate = cDate;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(String invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public String getInvoiceFinancialYear() {
        return invoiceFinancialYear;
    }

    public void setInvoiceFinancialYear(String invoiceFinancialYear) {
        this.invoiceFinancialYear = invoiceFinancialYear;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getInvoiceMobile() {
        return invoiceMobile;
    }

    public void setInvoiceMobile(String invoiceMobile) {
        this.invoiceMobile = invoiceMobile;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getInvoiceEmailId() {
        return invoiceEmailId;
    }

    public void setInvoiceEmailId(String invoiceEmailId) {
        this.invoiceEmailId = invoiceEmailId;
    }

    public String getInvoiceCity() {
        return invoiceCity;
    }

    public void setInvoiceCity(String invoiceCity) {
        this.invoiceCity = invoiceCity;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public double getInvoiceTotalAmount() {
        return invoiceTotalAmount;
    }

    public void setInvoiceTotalAmount(double invoiceTotalAmount) {
        this.invoiceTotalAmount = invoiceTotalAmount;
    }

    public double getInvoicePayable() {
        return invoicePayable;
    }

    public void setInvoicePayable(double invoicePayable) {
        this.invoicePayable = invoicePayable;
    }

    public double getInvoiceRemainingAmount() {
        return invoiceRemainingAmount;
    }

    public void setInvoiceRemainingAmount(double invoiceRemainingAmount) {
        this.invoiceRemainingAmount = invoiceRemainingAmount;
    }

    public double getInvoiceReceivedAmount() {
        return invoiceReceivedAmount;
    }

    public void setInvoiceReceivedAmount(double invoiceReceivedAmount) {
        this.invoiceReceivedAmount = invoiceReceivedAmount;
    }

    public String getInvoicePincode() {
        return invoicePincode;
    }

    public void setInvoicePincode(String invoicePincode) {
        this.invoicePincode = invoicePincode;
    }

    public String getInvoiceDeliveryCharges() {
        return invoiceDeliveryCharges;
    }

    public void setInvoiceDeliveryCharges(String invoiceDeliveryCharges) {
        this.invoiceDeliveryCharges = invoiceDeliveryCharges;
    }

    public double getInvoiceDiscount() {
        return invoiceDiscount;
    }

    public void setInvoiceDiscount(double invoiceDiscount) {
        this.invoiceDiscount = invoiceDiscount;
    }

    public double getInvoiceCouponCodeDiscount() {
        return invoiceCouponCodeDiscount;
    }

    public void setInvoiceCouponCodeDiscount(double invoiceCouponCodeDiscount) {
        this.invoiceCouponCodeDiscount = invoiceCouponCodeDiscount;
    }

    public CouponCode getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(CouponCode couponCode) {
        this.couponCode = couponCode;
    }

    public int getInvoicePaymentMode() {
        return invoicePaymentMode;
    }

    public void setInvoicePaymentMode(int invoicePaymentMode) {
        this.invoicePaymentMode = invoicePaymentMode;
    }

    public String getInvoiceGeneratedBy() {
        return invoiceGeneratedBy;
    }

    public void setInvoiceGeneratedBy(String invoiceGeneratedBy) {
        this.invoiceGeneratedBy = invoiceGeneratedBy;
    }

    public int getInvoiceUpdatedBy() {
        return invoiceUpdatedBy;
    }

    public void setInvoiceUpdatedBy(int invoiceUpdatedBy) {
        this.invoiceUpdatedBy = invoiceUpdatedBy;
    }

    public String getInvoiceUpdatedDate() {
        return invoiceUpdatedDate;
    }

    public void setInvoiceUpdatedDate(String invoiceUpdatedDate) {
        this.invoiceUpdatedDate = invoiceUpdatedDate;
    }

    public String getInvoiceDeliveryDate() {
        return invoiceDeliveryDate;
    }

    public void setInvoiceDeliveryDate(String invoiceDeliveryDate) {
        this.invoiceDeliveryDate = invoiceDeliveryDate;
    }

    public String getInvoiceSpecialInstruction() {
        return invoiceSpecialInstruction;
    }

    public void setInvoiceSpecialInstruction(String invoiceSpecialInstruction) {
        this.invoiceSpecialInstruction = invoiceSpecialInstruction;
    }

    public DeliveryBoy getDeliveryBoy() {
        return deliveryBoy;
    }

    public void setDeliveryBoy(DeliveryBoy deliveryBoy) {
        this.deliveryBoy = deliveryBoy;
    }

    public DeliveryTimeSlot getDeliveryTimeSlot() {
        return deliveryTimeSlot;
    }

    public void setDeliveryTimeSlot(DeliveryTimeSlot deliveryTimeSlot) {
        this.deliveryTimeSlot = deliveryTimeSlot;
    }

    public String getInvoiceIsHold() {
        return invoiceIsHold;
    }

    public void setInvoiceIsHold(String invoiceIsHold) {
        this.invoiceIsHold = invoiceIsHold;
    }

    public int getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(int invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceRazorpayOrderId() {
        return invoiceRazorpayOrderId;
    }

    public void setInvoiceRazorpayOrderId(String invoiceRazorpayOrderId) {
        this.invoiceRazorpayOrderId = invoiceRazorpayOrderId;
    }

    public int getInvoiceRazorpayStatus() {
        return invoiceRazorpayStatus;
    }

    public void setInvoiceRazorpayStatus(int invoiceRazorpayStatus) {
        this.invoiceRazorpayStatus = invoiceRazorpayStatus;
    }

    public String getInvoiceRazorpayPaymentId() {
        return invoiceRazorpayPaymentId;
    }

    public void setInvoiceRazorpayPaymentId(String invoiceRazorpayPaymentId) {
        this.invoiceRazorpayPaymentId = invoiceRazorpayPaymentId;
    }

    public String getInvoiceRazorpaySignature() {
        return invoiceRazorpaySignature;
    }

    public void setInvoiceRazorpaySignature(String invoiceRazorpaySignature) {
        this.invoiceRazorpaySignature = invoiceRazorpaySignature;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }
}
