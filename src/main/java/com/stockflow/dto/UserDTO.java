package com.stockflow.dto;

import com.stockflow.model.user.User;
import com.stockflow.model.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public record UserDTO(

        UUID id,

        @NotBlank(message = "User login cannot be blank or empty")
        String login,

        @NotBlank(message = "User password cannot be blank or empty")
        String password,

        @NotNull(message = "User role cannot be empty")
        UserRole role) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserDTO(User user){
        this(user.getId(), user.getLogin(), user.getPassword(), user.getRole());
    }
}