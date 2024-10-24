package com.stockflow.services;

import com.stockflow.dto.userDtos.UserRequestDTO;
import com.stockflow.dto.userDtos.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO create(UserRequestDTO userRequestDTO);

    UserResponseDTO update(UserRequestDTO userRequestDTO);

    UserResponseDTO findById(UUID id);

    List<UserResponseDTO> listAll();

    void delete(UUID id);
}
