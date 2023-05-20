package com.example.Hirex.controller;
import com.example.Hirex.entity.LoginRequest;
import com.example.Hirex.entity.SignUpRequest;
import com.example.Hirex.entity.User;
import com.example.Hirex.service.AuthServiceImpl;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
        Map<String,String> token = new HashMap<>();
        token.put("token",jwt);
        return ResponseEntity.status(201).body(token);
    }

    @PostMapping("/login")
    public  ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        String jwt = authService.loginUser(loginRequest);
        if(jwt==null){
            return ResponseEntity.status(401).body("Invalid Credentials");
        }
        Token token = new Token(jwt);
        Cookie cookie = new Cookie("access_token", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Set to true if using HTTPS
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        // Add the cookie to the response
        return ResponseEntity .status(200).headers(headers).body(token);
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
class Token{
    String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
