package com.example.grocery_shop_backend.Exception;

import com.example.grocery_shop_backend.Dto.errorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MobileNumberExceptionHandler
{
    @ExceptionHandler(MobileNumberAlreadyExistsException.class)
    public ResponseEntity<errorResponse> handleException(MobileNumberAlreadyExistsException ex)
    {
        errorResponse errorResponse = new errorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}