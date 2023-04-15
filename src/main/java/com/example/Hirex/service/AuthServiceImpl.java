package com.example.Hirex.service;

import com.example.Hirex.entity.LoginRequest;
import com.example.Hirex.entity.SignUpRequest;
import com.example.Hirex.entity.User;
import com.example.Hirex.repository.UserRepository;
import com.example.Hirex.utlil.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Hirex.api.AuthService;
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public String  loginUser(LoginRequest request) {
         User user = userRepository.findByEmail(request.getEmail());
         if(user==null){
             return  null;
         }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        System.out.println("pass "+request.getPassword()+" "+encodedPassword+passwordEncoder.matches(request.getPassword(),encodedPassword));
         if(!passwordEncoder.matches(request.getPassword(),encodedPassword)){
             return null;
         }
         return jwtUtils.generateToken(request.getEmail()+" "+request.getPassword());

    }
    @Override
    public String signUpUser(SignUpRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        System.out.println("pass "+request.getPassword()+" "+encodedPassword);
        if (user==null){
            user= new User();
            user.setEmail(request.getEmail());
            user.setPassword(encodedPassword);
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
