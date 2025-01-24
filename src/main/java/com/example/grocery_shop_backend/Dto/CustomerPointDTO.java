package com.example.grocery_shop_backend.Dto;

public class CustomerPointDTO
{
    private double points;
    private String details;
    private int pointType; // 1 = IN & 2 = OUT

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getPointType() {
        return pointType;
    }

    public void setPointType(int pointType) {
        this.pointType = pointType;
    }
}
