package com.stockflow.controllers;

import com.stockflow.dto.userDtos.SignInRequestDTO;
import com.stockflow.dto.userDtos.SignInResponseDTO;
import com.stockflow.dto.userDtos.SignUpRequestDTO;
import com.stockflow.dto.userDtos.SignUpResponseDTO;
import com.stockflow.model.user.User;
import com.stockflow.repositories.UserRepository;
import com.stockflow.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Management", description = "Endpoints for managing user authentication, including login, registration, password management, and token handling.")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository repository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @PostMapping("/signin")
    @Operation(
            summary = "Authenticate a user",
            description = "Authenticate a user by validating login credentials and returning a JWT token upon successful authentication.",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = @Content(schema = @Schema(implementation = SignInResponseDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<SignInResponseDTO> signin(@RequestBody @Valid SignInRequestDTO data) {
        logger.info("Receive request to sign-in.");

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password()); // Gen the UsernamePass
        var auth = this.authenticationManager.authenticate(usernamePassword); // Validate given UsernamePass
        var token = tokenService.generateToken((User) auth.getPrincipal()); // Gen token from UsernamePass

        logger.info("Request to sign-in processed successfully.");
        return ResponseEntity.ok(new SignInResponseDTO(token));
    }

    @PostMapping("/signup")
    @Operation(
            summary = "Register a new user",
            description = "Register a new user by validating the input data and saving the encrypted password in the database.",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<SignUpResponseDTO> signup(@RequestBody @Valid SignUpRequestDTO data) {
        logger.info("Receive request to sign-up.");

        if (this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password()); // Encrypts the password
        User newUser = new User(data); // Create a user with unencrypted password
        newUser.setPassword(encryptedPassword); // Set the password as encrypted password
        this.repository.save(newUser); // Persist user in DB

        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO(newUser);
        logger.info("Request to sign-up processed successfully.");
        return ResponseEntity.ok(signUpResponseDTO);
    }
}
