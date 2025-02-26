package com.example.grocery_shop_backend.Dto;

public class CustomerUpdatePlaceOrderDTO
{
    private int cityId;
    private String pincode;
    private double points;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
