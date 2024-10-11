package com.gustavo.stockflowapi.services;

import com.gustavo.stockflowapi.domain.user.User;
import com.gustavo.stockflowapi.dtos.UserDTO;
import com.gustavo.stockflowapi.exceptions.InvalidUserDataException;
import com.gustavo.stockflowapi.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    private static boolean userDataValidation(UserDTO userDTO){
        if(userDTO.name().isEmpty() || userDTO.name().isBlank()){
            throw new InvalidUserDataException("Name cannot be empty or null");
        }
        if(userDTO.login().isEmpty()){
            throw new InvalidUserDataException("Login cannot be empty or null");
        }
        if(userDTO.password().isEmpty() || userDTO.password().isBlank()){
            throw new InvalidUserDataException("Password cannot be empty or null");
        }
        if(userDTO.role() == null){
            throw new InvalidUserDataException("Role cannot be empty or null");
        }

        return true;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        if(userDataValidation(userDTO)){
            repository.save(new User(userDTO));
        }
        return null;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        return null;
    }

    @Override
    public UUID findById(UUID id) {
        return null;
    }

    @Override
    public List<UserDTO> listAll() {
        return List.of();
    }

    @Override
    public void delete(UUID id) {

    }
}
