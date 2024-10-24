package com.stockflow.dto.userDtos;

import com.stockflow.model.user.UserRole;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;

public record SignUpRequestDTO(

        @NotBlank(message = "Login cannot be blank or empty")
        String login,

        @NotBlank(message = "Password cannot be blank or empty")
        String password,

        @NotBlank(message = "User role cannot be blank or empty")
        UserRole role) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
