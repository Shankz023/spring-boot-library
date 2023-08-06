package com.luv2code.springbootlibrary;

import com.luv2code.springbootlibrary.exceptions.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException serviceException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred "+serviceException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred "+exception.getMessage());
    }
}
