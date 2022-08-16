package com.example.jwttoken.controllers;

import com.example.jwttoken.entities.User;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        var users = List.of(
                new User(1L, "Tomas", "Svojanovsky", "tomas@gmail.com"),
                new User(2L, "Giovanni", "Seredovanni", "giorgino@gmail.com")
        );

        return ResponseEntity.ok().body(users);
    }
}
