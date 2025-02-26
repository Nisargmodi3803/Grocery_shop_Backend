package com.example.grocery_shop_backend.Dto;

import com.example.grocery_shop_backend.Entities.Cart;

public class AddProductOrderDTO
{
    private Cart[] carts;
    private int orderNum;
    private double totalPayable;

    public Cart[] getCarts() {
        return carts;
    }

    public void setCarts(Cart[] carts) {
        this.carts = carts;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }


    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }
}
