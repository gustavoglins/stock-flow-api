package com.gustavo.stockflowapi.exceptions.handler;

import com.gustavo.stockflowapi.dtos.ErrorResponseDTO;
import com.gustavo.stockflowapi.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<ErrorResponseDTO> handlerUnexpectException(UnexpectedException e, HttpServletRequest request) {
        logger.error("Unexpected error: {}", e.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                "An unexpected error occurred",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InvalidProductDataException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidProductDataException(InvalidProductDataException e, HttpServletRequest request) {
        logger.error("Invalid product data: {}", e.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "Invalid product data",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicateProductException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateProductException(DuplicateProductException e, HttpServletRequest request) {
        logger.error("Duplicate product: {}", e.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                "Product already exists",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductNotFoundException(ProductNotFoundException e, HttpServletRequest request) {
        logger.error("Product not found: {}", e.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                "Product not found",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidUserDataException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidUserDataException(InvalidUserDataException e, HttpServletRequest request) {
        logger.error("Invalid user data: {}", e.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "Invalid user data",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
