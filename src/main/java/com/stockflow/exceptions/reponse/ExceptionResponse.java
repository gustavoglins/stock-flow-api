package com.stockflow.exceptions.reponse;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, String details, LocalDateTime timestamp) {
}
