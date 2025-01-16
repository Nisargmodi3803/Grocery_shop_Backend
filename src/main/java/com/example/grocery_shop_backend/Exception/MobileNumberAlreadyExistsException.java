package com.example.grocery_shop_backend.Exception;

public class MobileNumberAlreadyExistsException extends RuntimeException
{
    public MobileNumberAlreadyExistsException(String message)
    {
        super(message);
    }
}
