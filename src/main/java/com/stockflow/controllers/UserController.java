package com.stockflow.controllers;

import com.stockflow.dto.UserDTO;
import com.stockflow.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping(value = "/api/user")
@Tag(name = "User", description = "Endpoints for user actions.")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(
            summary = "Create a new user",
            description = "Create a new User",
            tags = {"User"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content()),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content())
            })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO) {
        logger.info("Received request to create a new user.");
        UserDTO createdUser = service.create(userDTO);
        logger.info("Request to create a new user processed successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(
            summary = "Update an user",
            description = "Update an user",
            tags = {"User"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content()),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content()),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content()),
            }
    )
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserDTO userDTO) {
        logger.info("Receive request to update an user with ID: {}.", userDTO.id());
        UserDTO updatedUser = service.update(userDTO);
        logger.info("Request to update user with ID: {} processed successfully.", userDTO.id());
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(
            summary = "Find an user by ID",
            description = "Find an user by ID",
            tags = {"User"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content()),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content()),
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
        logger.info("Receive request to find an user with ID: {}.", id);
        UserDTO foundUser = service.findById(id);
        logger.info("Request to find user with ID: {} processed successfully.", id);
        return ResponseEntity.ok(foundUser);
    }

    @Operation(
            summary = "List all users registered",
            description = "List all users registered",
            tags = {"User"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))
                            )
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content()),
            }
    )
    @GetMapping
    public ResponseEntity<List<UserDTO>> listAll() {
        logger.info("Receive request to list all users registered.");
        List<UserDTO> userDTOList = service.listAll();
        logger.info("Request to list all users processed successfully. Total users: {}.", userDTOList.size());
        return ResponseEntity.ok(userDTOList);
    }

    @Operation(
            summary = "Delete an user by ID",
            description = "Delete an user by ID",
            tags = {"User"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content()),
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        logger.info("Receive request to delete an user with ID: {}.", id);
        service.delete(id);
        logger.info("Request to delete user with ID: {} processed successfully.", id);
        return ResponseEntity.ok("User with ID: " + id + "  deleted successfully.");
    }
}
