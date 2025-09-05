package com.example.springboothomework.controller.request;

import com.example.springboothomework.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class RegisterUserRequest {
    @NotBlank
    String email;

    @NotBlank
    String username;

    @NotBlank
    String password;

    @NotBlank
    String role;
}
