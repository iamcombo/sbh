package com.example.springboothomework.service;

import com.example.springboothomework.controller.request.LoginUserRequest;
import com.example.springboothomework.controller.request.RegisterUserRequest;
import com.example.springboothomework.controller.response.LoginUserResponse;
import com.example.springboothomework.controller.response.RegisterUserResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<RegisterUserResponse> register(RegisterUserRequest body);
    ResponseEntity<LoginUserResponse> login(LoginUserRequest body);
}
