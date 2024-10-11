package com.gustavo.stockflowapi.services;

import com.gustavo.stockflowapi.domain.user.User;
import com.gustavo.stockflowapi.dtos.UserDTO;
import com.gustavo.stockflowapi.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        return new UserDTO(repository.save(new User()));
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
