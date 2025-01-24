package com.example.grocery_shop_backend.Exception;

public class InsufficientPointsException extends RuntimeException
{
    public InsufficientPointsException(String message)
    {
        super(message);
    }
}
