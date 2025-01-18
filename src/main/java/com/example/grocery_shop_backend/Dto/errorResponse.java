package com.example.grocery_shop_backend.Dto;

import java.time.LocalTime;

public class errorResponse
{
    private int status;
    private String message;
    private String timeStamp;

    public errorResponse(int status, String message, String timeStamp)
    {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}