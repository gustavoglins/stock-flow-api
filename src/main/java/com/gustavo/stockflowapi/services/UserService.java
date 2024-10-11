package com.gustavo.stockflowapi.services;

import com.gustavo.stockflowapi.dtos.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    UserDTO create(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    UUID findById(UUID id);

    List<UserDTO> listAll();

    void delete(UUID id);
}
