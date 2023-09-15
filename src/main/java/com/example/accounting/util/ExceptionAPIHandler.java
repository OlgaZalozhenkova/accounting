package com.example.accounting.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAPIHandler {
    @ExceptionHandler
    private ResponseEntity<GoodErrorResponse> handleException(GoodNotFoundException e) {
        GoodErrorResponse response = new GoodErrorResponse
                ("Good was not found!",
                        System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);//404-статус
    }

    @ExceptionHandler
    private ResponseEntity<GoodErrorResponse> handleException(GoodNotCreatedException e) {
        GoodErrorResponse response = new GoodErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<GoodErrorResponse> handleException(SupplierNotFoundException e) {
        GoodErrorResponse response = new GoodErrorResponse
                ("Supplier was not found!",
                        System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);//404-статус
    }

}
