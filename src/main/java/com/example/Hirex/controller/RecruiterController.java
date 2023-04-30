package com.example.Hirex.controller;

import com.example.Hirex.entity.AuthRequest;
import com.example.Hirex.entity.Recruiter;
import com.example.Hirex.entity.RecruiterRequest;
import com.example.Hirex.entity.response.RecruiterResponse;
import com.example.Hirex.service.RecruiterServiceImpl;
import com.example.Hirex.utlil.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
    @Autowired
    private RecruiterServiceImpl recruiterService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RecruiterRequest recruiter) throws ApiError {
        Recruiter savedRecruiter;
        try {
            savedRecruiter = recruiterService.createRecruiter(recruiter);
            return new ResponseEntity<>(savedRecruiter, HttpStatus.CREATED);
        }
        catch (ApiError error){
            return  ResponseEntity.status(error.getStatus()).body(error.getMessage());
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request ) throws ApiError {
        try{
            System.out.println(request.getEmail()+request.getPassword());
            String token = recruiterService.login(request.getEmail(), request.getPassword());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        catch (ApiError error){
            return new ResponseEntity<ApiError>(error.getStatus());
        }
    }
    @GetMapping("/")
    public ResponseEntity<?> getRecruiter(@RequestHeader("token") String token){
        try {
            RecruiterResponse recruiter = recruiterService.getRecruiter(token);
            return ResponseEntity.status(HttpStatus.OK).body(recruiter);
        } catch (ApiError e) {
            return   ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}
