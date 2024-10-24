package com.stockflow.services;

import com.stockflow.controllers.UserController;
import com.stockflow.dto.userDtos.UserRequestDTO;
import com.stockflow.dto.userDtos.UserResponseDTO;
import com.stockflow.exceptions.UserNotFoundException;
import com.stockflow.model.user.User;
import com.stockflow.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        logger.info("Creating a new newUser with login: {}", userRequestDTO.login());
        String encryptedPassword = passwordEncoder.encode(userRequestDTO.password());

        User newUser = new User(userRequestDTO);
        newUser.setPassword(encryptedPassword);

        newUser.add(linkTo(methodOn(UserController.class).create(userRequestDTO)).withSelfRel()); // Adding link hateoas

        UserResponseDTO createdUser = new UserResponseDTO(repository.save(newUser));
        logger.info("User created successfully with ID: {}", createdUser.id());

        return createdUser;
    }

    @Override
    public UserResponseDTO update(UserRequestDTO userRequestDTO) {
        logger.info("Updating user with ID: {}.", userRequestDTO.id());
        Optional<User> optionalUser = repository.findById(userRequestDTO.id());

        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            logger.debug("User found for update: {}", foundUser);

            foundUser.setLogin(userRequestDTO.login());
            foundUser.setPassword(userRequestDTO.password());
            foundUser.setRole(userRequestDTO.role());

            foundUser.add(linkTo(methodOn(UserController.class).update(userRequestDTO)).withSelfRel()); // Adding link hateoas

            UserResponseDTO updatedUser = new UserResponseDTO(repository.save(foundUser));
            logger.info("User with ID: {} updated successfully.", updatedUser.id());
            return updatedUser;
        } else {
            logger.error("User with ID: {} not found for update.", userRequestDTO.id());
            throw new UserNotFoundException("User with ID: " + userRequestDTO.id() + " not found.");
        }
    }

    @Override
    public UserResponseDTO findById(UUID id) {
        logger.info("Searching for user with ID: {}", id);
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();

            foundUser.add(linkTo(methodOn(UserController.class).findById(foundUser.getId())).withSelfRel()); // Adding link hateoas

            logger.info("User with ID: {} found successfully.", foundUser.getId());
            return new UserResponseDTO(foundUser);
        } else {
            logger.error("User with ID: {} not found.", id);
            throw new UserNotFoundException("User with ID: " + id + " not found.");
        }
    }

    @Override
    public List<UserResponseDTO> listAll() {
        logger.info("Listing all registered users.");
        List<User> userList = repository.findAll();

        userList.forEach(user -> user.add(linkTo(methodOn(UserController.class).listAll()).withSelfRel())); // Adding link hateoas

        logger.info("Total users found: {}.", userList.size());
        return userList.stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        logger.info("Deleting user with ID: {}.", id);
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
