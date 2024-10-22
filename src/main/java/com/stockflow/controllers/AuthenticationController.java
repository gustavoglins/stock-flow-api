package com.stockflow.controllers;

import com.stockflow.dto.AuthenticationDTO;
import com.stockflow.dto.RegisterDTO;
import com.stockflow.dto.SignInResponseDTO;
import com.stockflow.model.user.User;
import com.stockflow.repositories.UserRepository;
import com.stockflow.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDTO> signin(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new SignInResponseDTO(token));
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password()); // Encrypts the password

        User newUser = new User(data); // Create a user with unencrypted password
        newUser.setPassword(encryptedPassword); // Set the password as encrypted password

        this.repository.save(newUser); // Persist in user in DB
        return ResponseEntity.ok().build();
    }
}
