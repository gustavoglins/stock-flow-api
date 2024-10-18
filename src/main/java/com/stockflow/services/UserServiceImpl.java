package com.stockflow.services;

import com.stockflow.dto.UserDTO;
import com.stockflow.exceptions.UserNotFoundException;
import com.stockflow.model.user.User;
import com.stockflow.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        return new UserDTO(repository.save(new User(userDTO)));
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        Optional<User> optionalUser = repository.findById(userDTO.id());
        if (optionalUser.isPresent()) {
            User retrievedUser = optionalUser.get();

            retrievedUser.setLogin(userDTO.login());
            retrievedUser.setPassword(userDTO.password());
            retrievedUser.setRole(userDTO.role());

            return new UserDTO(repository.save(retrievedUser));
        } else {
            throw new UserNotFoundException("User with ID: " + userDTO.id() + " not found.");
        }
    }

    @Override
    public UserDTO findById(UUID id) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            return new UserDTO(optionalUser.get());
        } else {
            throw new UserNotFoundException("User with ID: " + id + " not found.");
        }
    }

    @Override
    public List<UserDTO> listAll() {
        List<User> userList = repository.findAll();
        return userList.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new UserNotFoundException("User with ID: " + id + " not found.");
        }
    }
}
