package com.example.grocery_shop_backend.Exception;

public class InvalidOTPException extends RuntimeException
{
    public InvalidOTPException(String message)
    {
        super(message);
    }
}
