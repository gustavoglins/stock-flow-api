package com.stockflow.dto.userDtos;

import com.stockflow.model.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.Links;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public record UserRequestDTO(

        UUID id,

        @NotBlank(message = "User login cannot be blank or empty")
        String login,

        @NotBlank(message = "User password cannot be blank or empty")
        String password,

        @NotNull(message = "User role cannot be empty")
        UserRole role,

        Links links) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
