package com.example.Hirex.api;

import com.example.Hirex.entity.LoginRequest;
import com.example.Hirex.entity.SignUpRequest;

public interface AuthService {
    public String loginUser(LoginRequest user);

    public  String  signUpUser(SignUpRequest user);

    public String getUser(String token);
}
