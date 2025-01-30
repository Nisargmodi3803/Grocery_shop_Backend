package com.example.grocery_shop_backend.Dto;

public class OTPResponseDTO
{
    private OtpStatus status;
    private String message;

    public OTPResponseDTO() {}

    public OTPResponseDTO(OtpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public OtpStatus getStatus() {
        return status;
    }

    public void setStatus(OtpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
