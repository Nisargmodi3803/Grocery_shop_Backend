package com.example.grocery_shop_backend.Exception;

public class DuplicateEntryException extends RuntimeException
{
    public DuplicateEntryException(String message){
        super(message);
    }
}
