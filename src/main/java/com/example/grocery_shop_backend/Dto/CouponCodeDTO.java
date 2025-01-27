package com.example.grocery_shop_backend.Dto;

public class CouponCodeDTO
{
    private String code;
    private String title;
    private String startDate;
    private String endDate;
    private int type; // 1= fix amount, 2=percentage
    private int codeFor; // 1 = Secret , 2 = General
    private double value;
    private double minValue;
    private double maxDiscount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCodeFor() {
        return codeFor;
    }

    public void setCodeFor(int codeFor) {
        this.codeFor = codeFor;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }
}
