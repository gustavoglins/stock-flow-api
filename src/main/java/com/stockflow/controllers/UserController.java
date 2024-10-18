package com.stockflow.controllers;

import com.stockflow.dto.UserDTO;
import com.stockflow.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService service;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        logger.info("Receive request to create a new user.");
        UserDTO createdUser = service.create(userDTO);
        logger.info("User created successfully with ID: {}.", createdUser.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        logger.info("Receive request to update an user with ID: {}.", userDTO.id());
        UserDTO updatedUser = service.update(userDTO);
        logger.info("User with ID: {} updated successfully.", updatedUser.id());
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
        logger.info("Receive request to find an user with ID: {}.", id);
        UserDTO foundUser = service.findById(id);
        logger.info("User with ID: {} found successfully.", id);
        return ResponseEntity.ok(foundUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listAll() {
        logger.info("Receive request to list all users registered.");
        List<UserDTO> userDTOList = service.listAll();
        logger.info("All registered users found successfully. Total users: {}.", userDTOList.size());
        return ResponseEntity.ok(userDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        logger.info("Receive request to delete an user with ID: {}.", id);
        service.delete(id);
        logger.info("User with ID: {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }
}
