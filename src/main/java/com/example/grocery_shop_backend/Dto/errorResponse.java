package com.example.grocery_shop_backend.Dto;

public class errorResponse
{
    private int status;
    private String message;
    private long timeStamp;

    public errorResponse(int status, String message)
    {
        this.status = status;
        this.message = message;
        this.timeStamp = System.currentTimeMillis();
    }

    public errorResponse(){ }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}