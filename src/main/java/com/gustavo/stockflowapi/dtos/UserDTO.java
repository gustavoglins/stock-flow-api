package com.gustavo.stockflowapi.dtos;

import com.gustavo.stockflowapi.domain.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public record UserDTO(

        Long id,

        @NotBlank(message = "Name cannot be blank or empty")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        @NotBlank(message = "Login cannot be blank or empty")
        @Size(max = 150, message = "Login cannot exceed 150 characters")
        String login,

        @NotBlank(message = "Password cannot be blank or empty")
        @Size(min = 8, message = "Password must be longer than 8 characters")
        String password,

        @NotBlank(message = "Role cannot be empty or null")
        UserRole role) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}