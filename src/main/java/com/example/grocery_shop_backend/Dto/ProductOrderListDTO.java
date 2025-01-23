package com.example.grocery_shop_backend.Dto;

public class ProductOrderListDTO
{
    private int detailId;
    private int productId;
    private int orderNum;
    private String productName;
    private String productVariantName;
    private double basePrice;
    private double mrp;
    private int quantity;
    private double totalPrice;
    private double totalPayable;

    public ProductOrderListDTO(){}

    public ProductOrderListDTO(int detailId, int productId, int orderNum, String productName, String productVariantName, double basePrice, double mrp, int quantity, double totalPrice, double totalPayable) {
        this.detailId = detailId;
        this.productId = productId;
        this.orderNum = orderNum;
        this.productName = productName;
        this.productVariantName = productVariantName;
        this.basePrice = basePrice;
        this.mrp = mrp;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.totalPayable = totalPayable;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }
}
