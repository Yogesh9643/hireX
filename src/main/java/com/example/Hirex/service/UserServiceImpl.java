package com.example.Hirex.service;

import com.example.Hirex.api.UserService;
import com.example.Hirex.entity.User;
import com.example.Hirex.entity.UserProfile;
import com.example.Hirex.entity.UserProfileRequest;
import com.example.Hirex.repository.UserProfileRepository;
import com.example.Hirex.repository.UserRepository;
import com.example.Hirex.utlil.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;
    private UserProfile userProfile;

    @Override
    public  UserProfile createUserProfile(UserProfileRequest userProfileRequest) throws ApiError {
        String email = userProfileRequest.getEmail();
        System.out.println("email "+email);
        User user = userRepository.findByEmail(email);
        System.out.println("user "+user);
        System.out.println("user "+ userProfileRequest.getEmail()+" "+user.getEmail()+user.getPassword());
        if(user == null){
            throw new ApiError(HttpStatus.NOT_FOUND,"User not found");
        }
        userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setCurrentCompany(userProfileRequest.getCurrentCompany());
        userProfile.setFullName(userProfileRequest.getFullName());
        userProfileRepository.save(userProfile);
        return userProfile;
    }
    @Override
    public UserProfile getUserProfile(String email) throws ApiError {
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new ApiError(HttpStatus.NOT_FOUND,"User not found");
        }
        Optional<UserProfile> userProfileOptional = Optional.ofNullable(userProfileRepository.findByUser(user));
        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            return  userProfile;
        } else {
            throw new ApiError(HttpStatus.NOT_FOUND,"User not found");
        }
    }
//
//    @Override
//    public UserProfile saveUserProfile(UserProfileRequest newUserProfile) throws ApiError{
//        User user = userRepository.findByEmail(newUserProfile.getEmail());
//        if (user==null){
//            throw new ApiError(HttpStatus.NOT_FOUND,"User not found");
//        }
//        userProfile = userProfileRepository.findByEmail(newUserProfile.getEmail());
//        if(userProfile==null){
//            userProfile = new UserProfile();
//        }
//        userProfile.setUser(user);
//        userProfile.setAddress(newUserProfile.getAddress());
//        userProfile.setCurrentCompany(newUserProfile.getCurrentCompany());
//        userProfile.setFullName(newUserProfile.getFullName());
//        userProfileRepository.save(userProfile);
//        return userProfile;
//    }
//
//    @Override
//    public UserProfile updateUserProfile(UserProfileRequest updatedUserProfile) throws ApiError {
//        UserProfile userProfile;
//        Optional<UserProfile> userProfileOptional = Optional.ofNullable(userProfileRepository.findByEmail(updatedUserProfile.getEmail()));
//        if (userProfileOptional.isPresent()) {
//            userProfile = userProfileOptional.get();
//            userProfile.setFullName(updatedUserProfile.getFullName());
//            userProfileRepository.save(userProfile);
//        } else {
//            throw new ApiError(HttpStatus.NOT_FOUND,"User not found");
//        }
//
//        return userProfile;
//    }
//
//    @Override
//    public void deleteUserProfile(Long userId) throws ApiError {
//        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userId);
//        if (userProfileOptional.isPresent()) {
//            UserProfile userProfile = userProfileOptional.get();
//            userProfileRepository.delete(userProfile);
//        } else {
//            throw new ApiError(HttpStatus.NOT_FOUND,"User not found");
//        }
//    }


}
