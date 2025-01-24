package com.example.grocery_shop_backend.Dto;

public class AddProductOrderDTO
{
    private int [] productId;
    private int orderNum;
    private int quantity;
    private double totalPayable;

    public AddProductOrderDTO(){ }

    public AddProductOrderDTO(int[] productId, int orderNum,int quantity, double totalPayable) {
        this.productId = productId;
        this.orderNum = orderNum;
        this.quantity = quantity;
        this.totalPayable = totalPayable;
    }

    public int[] getProductId() {
        return productId;
    }

    public void setProductId(int[] productId) {
        this.productId = productId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }
}
