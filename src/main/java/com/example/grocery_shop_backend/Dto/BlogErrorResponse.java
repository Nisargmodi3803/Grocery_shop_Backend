package com.example.grocery_shop_backend.Dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogErrorResponse
{
    private int status;
    private String message;
    private long timeStamp;

    public BlogErrorResponse(int status, String message)
    {
        this.status = status;
        this.message = message;
        this.timeStamp = System.currentTimeMillis();
    }

    public BlogErrorResponse(){ }
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