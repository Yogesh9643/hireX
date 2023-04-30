package com.example.Hirex.repository;


import com.example.Hirex.entity.User;
import com.example.Hirex.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // Define custom methods here if needed
    UserProfile findByUser(User user);
}
