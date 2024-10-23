package com.stockflow.exceptions.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.stockflow.exceptions.ProductNotFoundException;
import com.stockflow.exceptions.UserNotFoundException;
import com.stockflow.exceptions.reponse.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // --------------------------------- Method to facilite create ExceptionResponses ----------------------------------
    private ResponseEntity<ExceptionResponse> buildResponse(Exception exception, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                exception.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(exceptionResponse);
    }
    // ------------------------------- Method to facilite create ExceptionResponses End --------------------------------





    // ------------------------------------------------ Generic Exception ----------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericExceptions(Exception exception, WebRequest request) {
        logger.error("Unexpected error: {} - {}", exception.getClass().getSimpleName(), exception.getMessage(), exception);
        return buildResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    // ---------------------------------------------- Generic Exception End --------------------------------------------





    // -------------------------------------------- Validations Exceptions ---------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationsExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        logger.warn("Validation failed: {}", errors);

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                "Validation errors occurred.",
                errors.toString(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    // --------------------------------------- Validations Exceptions End ----------------------------------------------





    // -------------------------------------------- User Exceptions ----------------------------------------------------
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundExceptions(UserNotFoundException exception, WebRequest request) {
        logger.error("UserNotFoundException: {} - Request: {}", exception.getMessage(), request.getDescription(false));
        return buildResponse(exception, HttpStatus.NOT_FOUND, request);
    }
    // ------------------------------------------ User Exceptions End --------------------------------------------------





    // ------------------------------------------- Product Exceptions --------------------------------------------------
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFoundExceptions(ProductNotFoundException exception, WebRequest request) {
        logger.error("ProductNotFoundException: {} - Request: {}", exception.getMessage(), request.getDescription(false));
        return buildResponse(exception, HttpStatus.NOT_FOUND, request);
    }
    // ------------------------------------------ Product Exceptions End -----------------------------------------------






    // --------------------------------------------- Security Exceptions -----------------------------------------------
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsExceptions(BadCredentialsException exception, WebRequest request) {
        logger.error("BadCredentialsException: {} - Request: {}", exception.getMessage(), request.getDescription(false));
        return buildResponse(exception, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleCredentialsExpiredExceptions(BadCredentialsException exception, WebRequest request) {
        logger.error("CredentialsExpiredException: {} - Request: {}", exception.getMessage(), request.getDescription(false));
        return buildResponse(exception, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedExceptions(AccessDeniedException exception, WebRequest request) {
        logger.error("AccessDeniedException: {} - Request: {}", exception.getMessage(), request.getDescription(false));
        return buildResponse(exception, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(SessionAuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleSessionAuthenticationExceptions(AccessDeniedException exception, WebRequest request) {
        logger.error("SessionAuthenticationException: {} - Request: {}", exception.getMessage(), request.getDescription(false));
        return buildResponse(exception, HttpStatus.FORBIDDEN, request);
    }
    // ------------------------------------------- Security Exceptions End ---------------------------------------------





    // --------------------------------------------- JWT Exceptions ----------------------------------------------------
    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ExceptionResponse> handleJWTVerificationExceptions(JWTVerificationException exception, WebRequest request) {
        logger.error("SessionAuthenticationException: {} - Request: {}", exception.getMessage(), request.getDescription(false));
        return buildResponse(exception, HttpStatus.FORBIDDEN, request);
    }
    // -------------------------------------------- JWT Exceptions End -------------------------------------------------
}
