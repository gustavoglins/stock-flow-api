package com.gustavo.stockflowapi.exceptions.handler;

import com.gustavo.stockflowapi.exceptions.DuplicateProductException;
import com.gustavo.stockflowapi.exceptions.InvalidProductDataException;
import com.gustavo.stockflowapi.exceptions.ProductNotFoundException;
import com.gustavo.stockflowapi.exceptions.UnexpectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<String> handlerUnexpectException(UnexpectedException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(InvalidProductDataException.class)
    public ResponseEntity<String> handleInvalidProductDataException(InvalidProductDataException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(DuplicateProductException.class)
    public ResponseEntity<String> handleDuplicateProductException(DuplicateProductException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
