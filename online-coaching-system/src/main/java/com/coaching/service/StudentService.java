package com.coaching.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.coaching.dao.StudentDao;
import com.coaching.dao.UserDao;
import com.coaching.dto.StudentRequest;
import com.coaching.entity.Student;
import com.coaching.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
	
	 private final StudentDao studentDao;
	 private final UserDao userDao;

	 public Student createStudent(
	            StudentRequest request){

	        User user = new User();

	        user.setName(request.getName());
	        user.setEmail(request.getEmail());
	        user.setPassword(request.getPassword());
	        user.setRole("STUDENT");

	        user = userDao.save(user);

	        Student student = new Student();

	        student.setUser(user);
	        student.setAddress(request.getAddress());
	        student.setDob(request.getDob());
	        student.setPhone(request.getPhone());
	        student.setJoinDate(request.getJoinDate());

	        return studentDao.save(student);
	    }

	    public List<Student> getAllStudents(){
	        return studentDao.findAll();
	    }

	    public Student getStudentById(Long id){

	        return studentDao.findById(id)
	                .orElseThrow(() ->
	                new RuntimeException("Student Not Found"));
	    }
}
