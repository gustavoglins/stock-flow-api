package com.gustavo.stockflowapi.exceptions;

public class UnexpectedException extends RuntimeException {

    public UnexpectedException(String message) {
        super(message);
    }
}
