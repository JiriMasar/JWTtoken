package com.example.jwttoken.controllers;

import com.example.jwttoken.model.ErrorResponse;
import com.example.jwttoken.model.JwtRequest;
import com.example.jwttoken.model.JwtResponse;
import com.example.jwttoken.services.UserService;
import com.example.jwttoken.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public LoginController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody JwtRequest jwtRequest) {
        // poslat do authenticated manageru username a password,
        // ktere nam prijdou z requestu

        // nacist si usera a poslat ho do jwtutils a vygenerovat token
        // odeslat ho pryc
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid password or username"));
        }

        UserDetails userDetails = userService.loadUserByUsername((jwtRequest.getUsername()));
        String token = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok().body(new JwtResponse(token));
    }
}