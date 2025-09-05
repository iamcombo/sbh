package com.example.springboothomework.service.impl;

import com.example.springboothomework.controller.request.LoginUserRequest;
import com.example.springboothomework.controller.request.RegisterUserRequest;
import com.example.springboothomework.controller.response.LoginUserResponse;
import com.example.springboothomework.controller.response.RegisterUserResponse;
import com.example.springboothomework.entity.User;
import com.example.springboothomework.exception.BadRequestException;
import com.example.springboothomework.repository.UserRepository;
import com.example.springboothomework.service.AuthService;
import com.example.springboothomework.service.UserDetailService;
import com.example.springboothomework.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public ResponseEntity<RegisterUserResponse> register(RegisterUserRequest registerUserRequest) {
        final boolean isEmailExist = userRepository.existsByEmail(registerUserRequest.getEmail());
        final boolean isUsernameExist = userRepository.existsByUsername(registerUserRequest.getUsername());
        if (isEmailExist) {
            throw new BadRequestException("Email already exists!");
        }
        if (isUsernameExist) {
            throw new BadRequestException("Username already exists!");
        }

        // Encode password
        final String encodedPassword = passwordEncoder.encode(registerUserRequest.getPassword());

        User user = new User();
        user.setEmail(registerUserRequest.getEmail());
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(encodedPassword);
        user.setRole(registerUserRequest.getRole());
        userRepository.save(user);

        final UserDetails userDetails = userDetailService.loadUserByUsername(registerUserRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        final RegisterUserResponse registerUserResponse = new RegisterUserResponse(user, token);

        return ResponseEntity.ok(registerUserResponse);
    }

    @Override
    public ResponseEntity<LoginUserResponse> login(LoginUserRequest body) {
        var user = userRepository.findByEmailOrUsername(body.getUsername(), body.getUsername());
        var userDetails = userDetailService.loadUserByUsername(user.getUsername());
        var token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginUserResponse(token));
    }
}
