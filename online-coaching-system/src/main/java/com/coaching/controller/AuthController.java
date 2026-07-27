package com.coaching.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coaching.dto.AuthResponse;
import com.coaching.dto.LoginRequest;
import com.coaching.dto.RegisterRequest;
import com.coaching.entity.ApiResponse;
import com.coaching.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request){

        String message = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)

                .body(new ApiResponse<>(true,message,null));

    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        System.out.println("Generated Token = " + response.getToken());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Login Successful", response));
    }
}
