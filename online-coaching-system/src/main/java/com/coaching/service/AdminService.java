package com.coaching.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.coaching.dao.UserDao;
import com.coaching.entity.User;

@Service
public class AdminService {

	 private final UserDao userDao;

	    public AdminService(UserDao userDao) {
	        this.userDao = userDao;
	    }


	    public List<User> getAllUsers() {
	        return userDao.findAll();
	    }
}
