package com.coaching.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.coaching.dao.UserDao;
import com.coaching.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	
	private final UserDao userDao;

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User getUserById(Long id) {

        return userDao.findById(id)
                .orElseThrow(() ->
                new RuntimeException("User Not Found"));
    }
}
