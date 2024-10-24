package com.stockflow.dto.userDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.stockflow.model.user.User;
import com.stockflow.model.user.UserRole;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@JsonPropertyOrder({"id", "login", "role"})
public record SignUpResponseDTO(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("login")
        String login,

        @JsonProperty("role")
        UserRole role) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public SignUpResponseDTO(User user){
        this(user.getId(), user.getLogin(), user.getRole());
    }
}
