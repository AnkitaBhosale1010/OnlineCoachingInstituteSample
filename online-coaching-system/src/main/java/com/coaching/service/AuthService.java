package com.coaching.service;

import java.time.LocalDate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.coaching.JwtUtil;
import com.coaching.dao.StudentDao;
import com.coaching.dao.UserDao;
import com.coaching.dto.AuthResponse;
import com.coaching.dto.LoginRequest;
import com.coaching.dto.RegisterRequest;
import com.coaching.entity.Student;
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
    private final StudentDao studentDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

      public AuthResponse register(RegisterRequest request) {

    	// Check if email already exists
        if(userDao.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException("Email already exists");

        }

        // Create User
        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        user.setRole(request.getRole());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save User
        user = userDao.save(user);

        if (request.getRole().equalsIgnoreCase("STUDENT")) {

            Student student = new Student();

            student.setUser(user);

            // Default values
            student.setAddress("");
            student.setPhone("");
            student.setDob(null);
            student.setJoinDate(LocalDate.now());

            studentDao.save(student);
        }

        // ===========================

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole());

        return new AuthResponse(user.getUserId(),token,user.getName(),user.getEmail(),user.getRole());
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

    	    return new AuthResponse(user.getUserId(),token,user.getName(),user.getEmail(),user.getRole());
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