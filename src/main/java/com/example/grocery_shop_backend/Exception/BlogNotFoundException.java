package com.example.grocery_shop_backend.Exception;

public class BlogNotFoundException extends RuntimeException
{
    public BlogNotFoundException(String message)
    {
        super(message);
    }
}
