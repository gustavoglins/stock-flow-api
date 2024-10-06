package com.gustavo.stockflowapi.dtos;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public record ErrorResponseDTO(

        String message,
        int statusCode,
        LocalDateTime timestamp,
        String errorDescription,
        String path) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}

