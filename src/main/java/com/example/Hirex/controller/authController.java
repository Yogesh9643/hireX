package com.example.Hirex.controller;
import com.example.Hirex.entity.LoginRequest;
import com.example.Hirex.entity.SignUpRequest;
import com.example.Hirex.entity.User;
import com.example.Hirex.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class authController {
    @Autowired
    private AuthServiceImpl authService;
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {

        String jwt = authService.signUpUser(signUpRequest);
        if(jwt==null){
            return ResponseEntity.status(401).body("user already exists");
        }
        return ResponseEntity.status(201).body(jwt);
    }

    @GetMapping("/login")
    public  ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        String jwt = authService.loginUser(loginRequest);
        if(jwt==null){
            return ResponseEntity.status(401).body("Invalid Credentials");
        }
        return ResponseEntity.status(200).body(jwt);
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        String user = authService.getUser(token);
        if (user ==
                null) {
            ResponseEntity.status(200).body("Invalid Credentials");
        }
        return ResponseEntity.status(200).body(user);

    }

}
