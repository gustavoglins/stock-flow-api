package com.stockflow.dto.userDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.stockflow.model.user.User;
import com.stockflow.model.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.Links;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@JsonPropertyOrder({"id", "login", "password", "role", "links"})
public record UserRequestDTO(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("login")
        @NotBlank(message = "User login cannot be blank or empty")
        String login,

        @JsonProperty("password")
        @NotBlank(message = "User password cannot be blank or empty")
        String password,

        @JsonProperty("role")
        @NotNull(message = "User role cannot be empty")
        UserRole role,

        @JsonProperty("links")
        Links links) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserRequestDTO(User user) {
        this(user.getId(), user.getLogin(), user.getPassword(), user.getRole(), user.getLinks());
    }
}
