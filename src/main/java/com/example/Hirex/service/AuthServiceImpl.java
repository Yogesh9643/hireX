package com.example.Hirex.service;

import com.example.Hirex.entity.LoginRequest;
import com.example.Hirex.entity.SignUpRequest;
import com.example.Hirex.entity.User;
import com.example.Hirex.repository.UserRepository;
import com.example.Hirex.utlil.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Hirex.api.AuthService;
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    public String  loginUser(LoginRequest request) {
         User user = userRepository.findByEmail(request.getEmail());
         if(user==null){
             return  null;
         }
         if(!user.getPassword().equals(request.getPassword())){
             return null;
         }
         return jwtUtils.generateToken(request.getEmail()+" "+request.getPassword());

    }
    @Override
    public String signUpUser(SignUpRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user==null){
            user= new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            userRepository.save(user);
            return jwtUtils.generateToken(user.getEmail()+" "+user.getPassword());
        }
        return  null;
    }

    @Override
    public String getUser(String token) {
        return jwtUtils.extractUsername(token);
    }
}
