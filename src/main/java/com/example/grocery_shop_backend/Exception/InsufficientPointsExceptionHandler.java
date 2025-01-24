package com.example.grocery_shop_backend.Exception;

import com.example.grocery_shop_backend.Dto.errorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class InsufficientPointsExceptionHandler {

    @ExceptionHandler(InsufficientPointsException.class)
    public ResponseEntity<errorResponse> handleException(InsufficientPointsException ex) {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeStamp = time.format(formatter);

        errorResponse errorResponse = new errorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value()); // 400 Bad Request
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(timeStamp);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
