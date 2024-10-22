package com.stockflow.dto;

import com.stockflow.model.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
