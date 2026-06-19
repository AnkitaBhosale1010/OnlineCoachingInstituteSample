package com.coaching.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.coaching.dao.TeacherDao;
import com.coaching.dao.UserDao;
import com.coaching.dto.TeacherRequest;
import com.coaching.entity.Teacher;
import com.coaching.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherService {
	
	private final TeacherDao teacherDao;
	private final UserDao userDao;

	public Teacher createTeacher(TeacherRequest request){

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole("TEACHER");

        user = userDao.save(user);

        Teacher teacher = new Teacher();

        teacher.setUser(user);
        teacher.setExpertise(request.getExpertise());
        teacher.setQualification(request.getQualification());
        teacher.setPhone(request.getPhone());
        teacher.setJoinDate(request.getJoinDate());

        return teacherDao.save(teacher);
    }

    public List<Teacher> getAllTeachers(){
        return teacherDao.findAll();
    }
    
    public List<Teacher> searchTeacher(String expertise) {
        return teacherDao
                .findByExpertiseContainingIgnoreCase(expertise);
    }

    public Teacher getTeacherById(Long id){

        return teacherDao.findById(id)
                .orElseThrow(() ->
                new RuntimeException("Teacher Not Found"));
    }}
