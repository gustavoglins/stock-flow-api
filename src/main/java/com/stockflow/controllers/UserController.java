package com.stockflow.controllers;

import com.stockflow.dto.userDtos.UserRequestDTO;
import com.stockflow.dto.userDtos.UserResponseDTO;
import com.stockflow.services.UserService;
import com.stockflow.util.CustomMediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
@Tag(name = "User Management", description = "Endpoints for managing users, including creating, updating, retrieving, and deleting user information.")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(consumes = {CustomMediaType.APPLICATION_JSON, CustomMediaType.APPLICATION_XML, CustomMediaType.APPLICATION_YAML},
                 produces = {CustomMediaType.APPLICATION_JSON, CustomMediaType.APPLICATION_XML, CustomMediaType.APPLICATION_YAML})
    @Operation(
            summary = "Create a new user",
            description = "Create a new User",
            tags = {"User Management"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content(schema = @Schema(implementation = UserRequestDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content()),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content())
            }
    )
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        logger.info("Received request to create a new user.");
        UserResponseDTO createdUser = service.create(userRequestDTO);
        logger.info("Request to create a new user processed successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping(consumes = {CustomMediaType.APPLICATION_JSON, CustomMediaType.APPLICATION_XML, CustomMediaType.APPLICATION_YAML},
                produces = {CustomMediaType.APPLICATION_JSON, CustomMediaType.APPLICATION_XML, CustomMediaType.APPLICATION_YAML})
    @Operation(
            summary = "Update an user",
            description = "Update an user",
            tags = {"User Management"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = UserRequestDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content()),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content()),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content()),
            }
    )
    public ResponseEntity<UserResponseDTO> update(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        logger.info("Receive request to update an user with ID: {}.", userRequestDTO.id());
        UserResponseDTO updatedUser = service.update(userRequestDTO);
        logger.info("Request to update user with ID: {} processed successfully.", userRequestDTO.id());
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping(value = "/{id}", produces = {CustomMediaType.APPLICATION_JSON, CustomMediaType.APPLICATION_XML, CustomMediaType.APPLICATION_YAML})
    @Operation(
            summary = "Find an user by ID",
            description = "Find an user by ID",
            tags = {"User Management"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = UserRequestDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content()),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content()),
            }
    )
    public ResponseEntity<UserResponseDTO> findById(@PathVariable @Parameter(description = "The ID of the product to be found.") UUID id) {
        logger.info("Receive request to find an user with ID: {}.", id);
        UserResponseDTO foundUser = service.findById(id);
        logger.info("Request to find user with ID: {} processed successfully.", id);
        return ResponseEntity.ok(foundUser);
    }

    @GetMapping(produces = {CustomMediaType.APPLICATION_JSON, CustomMediaType.APPLICATION_XML, CustomMediaType.APPLICATION_YAML})
    @Operation(
            summary = "List all users registered",
            description = "List all users registered",
            tags = {"User Management"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserRequestDTO.class))
                            )
                    ),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content()),
            }
    )
    public ResponseEntity<List<UserResponseDTO>> listAll() {
        logger.info("Receive request to list all users registered.");
        List<UserResponseDTO> userRequestDTOList = service.listAll();
        logger.info("Request to list all users processed successfully. Total users returned: {}.", userRequestDTOList.size());
        return ResponseEntity.ok(userRequestDTOList);
    }

    @DeleteMapping(value = "/{id}", produces = CustomMediaType.TEXT_PLAIN)
    @Operation(
            summary = "Delete an user by ID",
            description = "Delete an user by ID",
            tags = {"User Management"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content()),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content()),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content()),
            }
    )
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "The ID of the user to be deleted.") UUID id) {
        logger.info("Receive request to delete an user with ID: {}.", id);
        service.delete(id);
        logger.info("Request to delete user with ID: {} processed successfully.", id);
        return ResponseEntity.ok("User with ID: " + id + "  deleted successfully.");
    }
}
