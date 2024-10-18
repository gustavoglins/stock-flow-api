package com.stockflow.services;

import com.stockflow.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO create(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    UserDTO findById(UUID id);

    List<UserDTO> listAll();

    void delete(UUID id);
}
