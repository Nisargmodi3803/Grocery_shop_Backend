package com.example.grocery_shop_backend.Exception;

import com.example.grocery_shop_backend.Dto.errorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.InvalidClassException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class InvalidOTPExceptionHandler
{
    @ExceptionHandler(InvalidClassException.class)
    public ResponseEntity<errorResponse> handleException(InvalidOTPException ex) {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeStamp = time.format(formatter);

        errorResponse errorResponse = new errorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(timeStamp);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
