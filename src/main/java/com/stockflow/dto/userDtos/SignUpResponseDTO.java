package com.stockflow.dto.userDtos;

import com.stockflow.model.user.User;
import com.stockflow.model.user.UserRole;

import java.util.UUID;

public record SignUpResponseDTO(UUID id, String login, UserRole role) {

    public SignUpResponseDTO(User user){
        this(user.getId(), user.getLogin(), user.getRole());
    }
}
