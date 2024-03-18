package com.auth.authentication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Object> handleAppException(AppException ex) {
        HttpStatus status = ex.getCode();
        String message = ex.getMessage();
        ApiError apiError = new ApiError(status, message);
        return ResponseEntity.status(status).body(apiError);
    }
}
