package com.example.Hirex.service;

import com.example.Hirex.entity.Recruiter;
import com.example.Hirex.entity.RecruiterRequest;
import com.example.Hirex.entity.response.RecruiterResponse;
import com.example.Hirex.repository.RecruiterRepository;
import com.example.Hirex.utlil.ApiError;
import com.example.Hirex.utlil.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecruiterServiceImpl {
    @Autowired
    private RecruiterRepository recruiterRepository;
    @Autowired
    private JwtUtils jwtUtils;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String  login(String email, String password) throws ApiError {
        Recruiter recruiter = recruiterRepository.findByEmail(email);
        ;
        if(recruiter==null){
            throw new ApiError(HttpStatus.BAD_REQUEST, "Invalid Credentials");
        }

        String encodedPassword = passwordEncoder.encode(password);
        System.out.println(password+" "+encodedPassword);
        if(!passwordEncoder.matches(password,recruiter.getPassword())){
            throw new ApiError(HttpStatus.BAD_REQUEST, "Invalid Credentials");
        }
        String token =  jwtUtils.generateToken(email+" "+password);
        return token;
    }
    public RecruiterResponse getRecruiter(String token) throws ApiError {
        String decoded = jwtUtils.extractUsername(token);
        String[] details  = decoded.split(" ");

        Recruiter recruiter =  recruiterRepository.findByEmail(details[0]);
        RecruiterResponse response = new RecruiterResponse();
        response.setCompany(recruiter.getCompany());
        response.setEmail(recruiter.getEmail());
        response.setName(recruiter.getName());
        if(recruiter==null){
            throw  new ApiError(HttpStatus.BAD_REQUEST,"Invalid token");
        }
        return  response;
    }

    public Recruiter createRecruiter(RecruiterRequest recruiter) throws ApiError {
        if(isEmailExist(recruiter.getEmail())){
            throw  new ApiError(HttpStatus.BAD_REQUEST,"User already exists");
        }
        Recruiter newRecruiter = new Recruiter();
        newRecruiter.setName(recruiter.getName());
        newRecruiter.setEmail(recruiter.getEmail());
        newRecruiter.setCompany(recruiter.getCompany());
        String encodedPassword = passwordEncoder.encode(recruiter.getPassword());
        System.out.println(recruiter.getPassword()+" "+encodedPassword);
        newRecruiter.setPassword(passwordEncoder.encode(recruiter.getPassword()));
        System.out.println(newRecruiter.getCompany()+newRecruiter.getEmail()+newRecruiter.getName()+newRecruiter.getPassword());
        return recruiterRepository.save(newRecruiter);
    }
    public boolean isEmailExist(String email){
        Recruiter recruiter =  recruiterRepository.findByEmail(email);
        return recruiter!=null;
    }
//
//    public Recruiter updateRecruiter(Long id, Recruiter recruiter) {
//        Optional<Recruiter> optionalRecruiter = recruiterRepository.findById(id);
//        if (optionalRecruiter.isPresent()) {
//            Recruiter existingRecruiter = optionalRecruiter.get();
//            existingRecruiter.setName(recruiter.getName());
//            existingRecruiter.setEmail(recruiter.getEmail());
//            existingRecruiter.setCompany(recruiter.getCompany());
//            return recruiterRepository.save(existingRecruiter);
//        } else {
//            throw new ApiError(HttpStatus.NOT_FOUND, "Recruiter with id " + id + " not found");
//        }
//    }
//
//    public void deleteRecruiter(Long id) {
//        Optional<Recruiter> optionalRecruiter = recruiterRepository.findById(id);
//        if (optionalRecruiter.isPresent()) {
//            recruiterRepository.delete(optionalRecruiter.get());
//        } else {
//            throw new ApiError(HttpStatus.NOT_FOUND, "Recruiter with id " + id + " not found");
//        }
//    }
}
