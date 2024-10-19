package com.stockflow.controllers;

import com.stockflow.dto.UserDTO;
import com.stockflow.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User", description = "Endpoints for user actions.")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserDTO> handleCreate(@RequestBody @Valid UserDTO userDTO) {
        logger.info("Receive request to create a new user.");
        UserDTO createdUser = service.create(userDTO);
        logger.info("Request to create a new user processed successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping
    public ResponseEntity<UserDTO> handleUpdate(@RequestBody @Valid UserDTO userDTO) {
        logger.info("Receive request to update an user with ID: {}.", userDTO.id());
        UserDTO updatedUser = service.update(userDTO);
        logger.info("Request to update user with ID: {} processed successfully.", userDTO.id());
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> handleFindById(@PathVariable UUID id) {
        logger.info("Receive request to find an user with ID: {}.", id);
        UserDTO foundUser = service.findById(id);
        logger.info("Request to find user by ID: {} processed successfully.", id);
        return ResponseEntity.ok(foundUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> handleListAll() {
        logger.info("Receive request to list all users registered.");
        List<UserDTO> userDTOList = service.listAll();
        logger.info("Request to list all users processed successfully. Total users: {}.", userDTOList.size());
        return ResponseEntity.ok(userDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> handleDelete(@PathVariable UUID id) {
        logger.info("Receive request to delete an user with ID: {}.", id);
        service.delete(id);
        logger.info("Request to delete user with ID: {} processed successfully.", id);
        return ResponseEntity.ok("User with ID: " + id + "  deleted successfully.");
    }
}
