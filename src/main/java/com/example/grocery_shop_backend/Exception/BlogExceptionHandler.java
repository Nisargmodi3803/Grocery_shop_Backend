package com.example.grocery_shop_backend.Exception;

import com.example.grocery_shop_backend.Dto.BlogErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BlogExceptionHandler
{
    @ExceptionHandler(BlogNotFoundException.class)
    public ResponseEntity<BlogErrorResponse> handleException(BlogNotFoundException ex)
    {
        BlogErrorResponse errorResponse = new BlogErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
