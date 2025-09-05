package com.example.springboothomework.controller.response;

import com.example.springboothomework.entity.User;
import lombok.Value;

@Value
public class RegisterUserResponse {
    User user;
    String accessToken;

    public RegisterUserResponse(User user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }
}
