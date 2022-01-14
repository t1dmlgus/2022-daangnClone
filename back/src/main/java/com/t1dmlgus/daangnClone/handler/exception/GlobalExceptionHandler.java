package com.t1dmlgus.daangnClone.handler.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<String> CustomApiException(CustomApiException e) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
