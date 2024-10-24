package com.stockflow.dto.userDtos;

import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;

public record SignInRequestDTO(

        @NotBlank(message = "Login cannot be blank or empty")
        String login,

        @NotBlank(message = "Password cannot be blank or empty")
        String password) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
