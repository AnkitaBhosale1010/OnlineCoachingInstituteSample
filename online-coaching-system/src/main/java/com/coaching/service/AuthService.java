package com.coaching.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coaching.JwtUtil;
import com.coaching.dao.UserDao;
import com.coaching.dto.AuthResponse;
import com.coaching.dto.LoginRequest;
import com.coaching.dto.RegisterRequest;
import com.coaching.entity.User;

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

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userDao.save(user);

        return "User Registered";	
    }

    public AuthResponse login(LoginRequest request) {

        User user = userDao.findByEmail(request.getEmail()).orElseThrow();

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())) {

            throw new RuntimeException("Invalid Password");
        }
        System.out.println("user: "+user.toString());

        String token =jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(token);
    }
}