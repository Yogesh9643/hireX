package com.example.Hirex.controller;

import com.example.Hirex.entity.UserProfile;
import com.example.Hirex.entity.UserProfileRequest;
import com.example.Hirex.service.UserServiceImpl;
import com.example.Hirex.utlil.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-profile")
public class profileController {

    @Autowired
    private UserServiceImpl userProfileService;

    @PostMapping("/")
    public ResponseEntity<?> createUserProfile(@RequestBody UserProfileRequest userProfile) {
        try {
            System.out.println("profile req"+userProfile.getFullName()+userProfile.getEmail());
            UserProfile savedProfile = userProfileService.createUserProfile(userProfile);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
        } catch (ApiError e) {
            return ResponseEntity.status(e.getStatus()).body(e);
        }
    }


    @GetMapping("/")
    public ResponseEntity<UserProfile> getUserProfile(@RequestHeader("email") String email) {
        try {
            UserProfile profile = userProfileService.getUserProfile(email);
            return ResponseEntity.ok(profile);
        } catch (ApiError e) {
            return ResponseEntity.status(e.getStatus()).body(null);
        }
    }

//    @PutMapping("/")
//    public ResponseEntity<?> updateUserProfile(@RequestBody UserProfileRequest userProfile) throws ApiError {
//        try {
//            UserProfile updatedProfile = userProfileService.updateUserProfile(userProfile);
//            return ResponseEntity.ok(updatedProfile);
//        } catch (ApiError error) {
//            return ResponseEntity.status(error.getStatus()).body(error.getMessage());
//        }
//    }
}

