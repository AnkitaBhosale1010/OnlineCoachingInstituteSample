package com.coaching.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.coaching.JwtUtil;
import com.coaching.dao.UserDao;
import com.coaching.dto.AuthResponse;
import com.coaching.dto.LoginRequest;
import com.coaching.dto.RegisterRequest;
import com.coaching.entity.User;
import com.coaching.exception.DuplicateResourceException;
import com.coaching.exception.ResourceNotFoundException;
import com.coaching.exception.UnauthorizedException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

      public String register(RegisterRequest request) {

        if(userDao.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException("Email already exists");

        }

        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        user.setRole(request.getRole());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userDao.save(user);

        return "Registration Successful";
    }

    public AuthResponse login(LoginRequest request) {

    	 User user = userDao.findByEmail(

    	            request.getEmail()).orElseThrow(() ->

    	                    new ResourceNotFoundException(

    	                            "User not found"));

    	    if(!passwordEncoder.matches(

    	            request.getPassword(),

    	            user.getPassword())) {

    	        throw new UnauthorizedException(

    	                "Invalid Password");

    	    }

    	    String token = jwtUtil.generateToken(

    	            user.getEmail(),

    	            user.getRole());

    	    return new AuthResponse(token,user.getName(),user.getEmail(),user.getRole());
    }
    
    public String changePassword(

            Long userId,

            String oldPassword,

            String newPassword){

        User user = userDao.findById(userId)

                .orElseThrow(() ->

                        new ResourceNotFoundException(

                                "User not found"));

        if(!passwordEncoder.matches(

                oldPassword,

                user.getPassword())){

            throw new UnauthorizedException(

                    "Old password is incorrect");

        }

        user.setPassword(

                passwordEncoder.encode(newPassword));

        userDao.save(user);

        return "Password Updated Successfully";
    }
}