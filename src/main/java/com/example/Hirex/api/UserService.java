package com.example.Hirex.api;

import com.example.Hirex.entity.User;
import com.example.Hirex.entity.UserProfile;
import com.example.Hirex.entity.UserProfileRequest;
import com.example.Hirex.utlil.ApiError;

public interface UserService {

    UserProfile createUserProfile(UserProfileRequest userProfile) throws ApiError;
    UserProfile getUserProfile(String email) throws ApiError;
//
//    public UserProfile saveUserProfile(UserProfileRequest userProfile) throws ApiError;
//    public UserProfile updateUserProfile(UserProfileRequest userProfile) throws ApiError;
//
//    void deleteUserProfile(Long userId) throws ApiError;
}
