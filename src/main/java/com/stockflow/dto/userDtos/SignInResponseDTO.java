package com.stockflow.dto.userDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({"token"})
public record SignInResponseDTO(

        @JsonProperty("token")
        String token) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
