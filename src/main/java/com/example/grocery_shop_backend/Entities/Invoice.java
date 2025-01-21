package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_invoice")
public class Invoice
{
    @Id
    @Column(name = "invoice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;

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

    @Column(name = "invoice_address")
    private String invoiceAddress;

    @Column(name = "invoice_total_amount")
    private Double invoiceTotalAmount;

    @Column(name = "invoice_payable")
    private Double invoicePayable;

    @Column(name = "invoice_remaining_amount")
    private Double invoiceRemainingAmount;

    @Column(name = "invoice_received_amount")
    private Double invoiceReceivedAmount;

    @Column(name = "invoice_pincode")
    private String invoicePincode;

    @Column(name = "invoice_delivery_charges")
    private String invoiceDeliveryCharges;

    @Column(name = "invoice_discount")
    private Double invoiceDiscount;

    @Column(name = "invoice_coupon_code_discount")
    private Double invoiceCouponCodeDiscount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_coupon_id")
    private CouponCode couponCode;

    @Column(name = "invoice_payment_mode")
    private Integer invoicePaymentMode; // 1 => COD & 2 => Online

    @Column(name = "invoice_updated_by")
    private Integer invoiceUpdatedBy;

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
    private Integer invoiceStatus; // 1 => Pending, 2 => Confirm, 3 => Dispatched, 4 => Delivered, 5 => Rejected, 6 => Canceled

    @Column(name = "invoice_razorpay_order_id")
    private String invoiceRazorpayOrderId;

    @Column(name = "invoice_razorpay_status")
    private Integer invoiceRazorpayStatus; // 1 => Pending, 2 => Success, 3 => Failure.

    @Column(name = "invoice_razorpay_payment_id")
    private String invoiceRazorpayPaymentId;

    @Column(name = "invoice_razorpay_signature")
    private String invoiceRazorpaySignature;

    @Column(name = "is_deleted")
    private Integer isDeleted; // 1 => Not deleted & 2 => Deleted

    @Column(name = "c_date")
    private String cDate;

    public Invoice(){}

    public Invoice(Integer invoiceId, String invoiceNum, String invoicePrefix, String invoiceDate, String invoiceTime, String invoiceFinancialYear, Customer customer, String invoiceMobile, String invoiceName, String invoiceEmailId, String invoiceAddress, Double invoiceTotalAmount, Double invoicePayable, Double invoiceRemainingAmount, Double invoiceReceivedAmount, String invoicePincode, String invoiceDeliveryCharges, Double invoiceDiscount, Double invoiceCouponCodeDiscount, CouponCode couponCode, Integer invoicePaymentMode, Integer invoiceUpdatedBy, String invoiceUpdatedDate, String invoiceDeliveryDate, String invoiceSpecialInstruction, DeliveryBoy deliveryBoy, DeliveryTimeSlot deliveryTimeSlot, String invoiceIsHold, Integer invoiceStatus, String invoiceRazorpayOrderId, Integer invoiceRazorpayStatus, String invoiceRazorpayPaymentId, String invoiceRazorpaySignature, Integer isDeleted, String cDate) {
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

    public Integer getInvoiceId() {
        return invoiceId != null ? invoiceId : 0;
    }

    public void setInvoiceId(Integer invoiceId) {
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

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public Double getInvoiceTotalAmount() {
        return invoiceTotalAmount != null ? invoiceTotalAmount : 0.0;
    }

    public void setInvoiceTotalAmount(Double invoiceTotalAmount) {
        this.invoiceTotalAmount = invoiceTotalAmount;
    }

    public Double getInvoicePayable() {
        return invoicePayable != null ? invoicePayable : 0.0;
    }

    public void setInvoicePayable(Double invoicePayable) {
        this.invoicePayable = invoicePayable;
    }

    public Double getInvoiceRemainingAmount() {
        return invoiceRemainingAmount != null ? invoiceRemainingAmount : 0.0;
    }

    public void setInvoiceRemainingAmount(Double invoiceRemainingAmount) {
        this.invoiceRemainingAmount = invoiceRemainingAmount;
    }

    public Double getInvoiceReceivedAmount() {
        return invoiceReceivedAmount != null ? invoiceReceivedAmount : 0.0;
    }

    public void setInvoiceReceivedAmount(Double invoiceReceivedAmount) {
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

    public Double getInvoiceDiscount() {
        return invoiceDiscount != null ? invoiceDiscount : 0.0;
    }

    public void setInvoiceDiscount(Double invoiceDiscount) {
        this.invoiceDiscount = invoiceDiscount;
    }

    public Double getInvoiceCouponCodeDiscount() {
        return invoiceCouponCodeDiscount != null ? invoiceCouponCodeDiscount : 0.0;
    }

    public void setInvoiceCouponCodeDiscount(Double invoiceCouponCodeDiscount) {
        this.invoiceCouponCodeDiscount = invoiceCouponCodeDiscount;
    }

    public CouponCode getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(CouponCode couponCode) {
        this.couponCode = couponCode;
    }

    public Integer getInvoicePaymentMode() {
        return invoicePaymentMode != null ? invoicePaymentMode : 0;
    }

    public void setInvoicePaymentMode(Integer invoicePaymentMode) {
        this.invoicePaymentMode = invoicePaymentMode;
    }

    public Integer getInvoiceUpdatedBy() {
        return invoiceUpdatedBy != null ? invoiceUpdatedBy : 0;
    }

    public void setInvoiceUpdatedBy(Integer invoiceUpdatedBy) {
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

    public Integer getInvoiceStatus() {
        return invoiceStatus != null ? invoiceStatus : 0;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceRazorpayOrderId() {
        return invoiceRazorpayOrderId;
    }

    public void setInvoiceRazorpayOrderId(String invoiceRazorpayOrderId) {
        this.invoiceRazorpayOrderId = invoiceRazorpayOrderId;
    }

    public Integer getInvoiceRazorpayStatus() {
        return invoiceRazorpayStatus != null ? invoiceRazorpayStatus : 0;
    }

    public void setInvoiceRazorpayStatus(Integer invoiceRazorpayStatus) {
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

    public Integer getIsDeleted() {
        return isDeleted != null ? isDeleted : 0;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }
}
