package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_invoice_detail")
public class InvoiceDetail
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private int detailId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "detail_product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "detail_invoice_id")
    private Invoice invoice;

    @Column(name = "detail_product_name")
    private String productName;

    @Column(name = "detail_product_variant_name")
    private String productVariantName;

    @Column(name = "detail_base_price")
    private double basePrice;

    @Column(name = "detail_mrp")
    private double mrp;

    @Column(name = "detail_quantity")
    private int quantity;

    @Column(name = "detail_total_amount")
    private double totalAmount;

    @Column(name = "detail_total_payable")
    private double totalPayable;

    @Column(name = "is_deleted")
    private int isDeleted;

    @Column(name = "c_date")
    private String cDate;

    public InvoiceDetail(){}

    public InvoiceDetail(int detailId, Product product, Invoice invoice, String productName, String productVariantName, double basePrice, double mrp, int quantity, double totalAmount, double totalPayable, int isDeleted, String cDate) {
        this.detailId = detailId;
        this.product = product;
        this.invoice = invoice;
        this.productName = productName;
        this.productVariantName = productVariantName;
        this.basePrice = basePrice;
        this.mrp = mrp;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.totalPayable = totalPayable;
        this.isDeleted = isDeleted;
        this.cDate = cDate;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductVariantName() {
        return productVariantName;
    }

    public void setProductVariantName(String productVariantName) {
        this.productVariantName = productVariantName;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
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
