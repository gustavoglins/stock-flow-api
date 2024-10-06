package com.gustavo.stockflowapi.dtos;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public record ErrorResponseDTO(

        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
