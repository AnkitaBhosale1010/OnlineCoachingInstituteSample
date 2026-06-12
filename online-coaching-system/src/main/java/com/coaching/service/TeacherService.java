package com.coaching.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.coaching.dao.TeacherDao;
import com.coaching.entity.Teacher;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherService {
	
	private final TeacherDao teacherDao;

    public List<Teacher> getAllTeachers() {
        return teacherDao.findAll();
    }

    public Teacher getTeacherById(Long id) {
        return teacherDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher Not Found"));
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherDao.save(teacher);
    }

    public Teacher updateTeacher(Long id, Teacher teacher) {

        Teacher existingTeacher = getTeacherById(id);

        existingTeacher.setName(teacher.getName());
        existingTeacher.setEmail(teacher.getEmail());
        existingTeacher.setPhone(teacher.getPhone());
        existingTeacher.setQualification(teacher.getQualification());
        existingTeacher.setExpertise(teacher.getExpertise());

        return teacherDao.save(existingTeacher);
    }

    public void deleteTeacher(Long id) {
    	teacherDao.deleteById(id);
    }
}
