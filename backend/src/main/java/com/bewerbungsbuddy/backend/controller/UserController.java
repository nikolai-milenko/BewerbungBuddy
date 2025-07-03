package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.UserRequestDto;
import com.bewerbungsbuddy.backend.dto.UserResponseDto;
import com.bewerbungsbuddy.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto dto) {
        UserResponseDto response = service.register(dto);
        return ResponseEntity.ok(response);
    }

    // TODO: Add POST endpoint for user login
    // e.g. @PostMapping("/login") to handle login with email and password
}
