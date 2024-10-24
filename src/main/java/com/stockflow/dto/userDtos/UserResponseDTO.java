package com.stockflow.dto.userDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.stockflow.model.user.User;
import com.stockflow.model.user.UserRole;
import org.springframework.hateoas.Links;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@JsonPropertyOrder({"id", "login", "role", "links"})
public record UserResponseDTO(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("login")
        String login,

        @JsonProperty("role")
        UserRole role,

        @JsonProperty("links")
        Links links) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserResponseDTO(User user) {
        this(user.getId(), user.getLogin(), user.getRole(), user.getLinks());
    }
}
