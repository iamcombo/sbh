package com.example.springboothomework.controller;

import com.example.springboothomework.controller.request.LoginUserRequest;
import com.example.springboothomework.controller.request.RegisterUserRequest;
import com.example.springboothomework.controller.response.LoginUserResponse;
import com.example.springboothomework.controller.response.RegisterUserResponse;
import com.example.springboothomework.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(
        @Valid @RequestBody RegisterUserRequest body
    ) {
        return authService.register(body);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(
        @Valid @RequestBody LoginUserRequest body
    ) {
        return authService.login(body);
    }
}