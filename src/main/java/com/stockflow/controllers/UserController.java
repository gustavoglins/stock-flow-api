package com.stockflow.controllers;

import com.stockflow.dto.UserDTO;
import com.stockflow.services.UserService;
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

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = service.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        UserDTO updatedUser = service.update(userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
        UserDTO foundUser = service.findById(id);
        return ResponseEntity.ok(foundUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listAll() {
        List<UserDTO> userDTOList = service.listAll();
        return ResponseEntity.ok(userDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
