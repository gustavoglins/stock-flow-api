package com.stockflow.services;

import com.stockflow.dto.UserDTO;
import com.stockflow.exceptions.UserNotFoundException;
import com.stockflow.model.user.User;
import com.stockflow.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        logger.info("Creating a new user with login: {}", userDTO.login());
        User createdUser = repository.save(new User(userDTO));
        logger.info("User created successfully with ID: {}", createdUser.getId());
        return new UserDTO(createdUser);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        logger.info("Updating user with ID: {}.", userDTO.id());
        Optional<User> optionalUser = repository.findById(userDTO.id());

        if (optionalUser.isPresent()) {
            User retrievedUser = optionalUser.get();
            logger.debug("User found for update: {}", retrievedUser);

            retrievedUser.setLogin(userDTO.login());
            retrievedUser.setPassword(userDTO.password());
            retrievedUser.setRole(userDTO.role());

            User updatedUser = repository.save(retrievedUser);
            logger.info("User with ID: {} updated successfully.", updatedUser.getId());
            return new UserDTO(updatedUser);
        } else {
            logger.error("User with ID: {} not found for update.", userDTO.id());
            throw new UserNotFoundException("User with ID: " + userDTO.id() + " not found.");
        }
    }

    @Override
    public UserDTO findById(UUID id) {
        logger.info("Searching for user with ID: {}", id);
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isPresent()) {
            logger.info("User with ID: {} found successfully.", id);
            return new UserDTO(optionalUser.get());
        } else {
            logger.error("User with ID: {} not found.", id);
            throw new UserNotFoundException("User with ID: " + id + " not found.");
        }
    }

    @Override
    public List<UserDTO> listAll() {
        logger.info("Listing all registered users.");
        List<User> userList = repository.findAll();
        logger.info("Total users found: {}", userList.size());
        return userList.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        logger.info("Attempting to delete user with ID: {}", id);
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isPresent()) {
            repository.deleteById(id);
            logger.info("User with ID: {} deleted successfully.", id);
        } else {
            logger.error("User with ID: {} not found for deletion.", id);
            throw new UserNotFoundException("User with ID: " + id + " not found.");
        }
    }
}
