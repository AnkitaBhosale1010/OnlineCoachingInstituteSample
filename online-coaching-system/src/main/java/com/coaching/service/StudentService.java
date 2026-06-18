package com.coaching.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.coaching.dao.StudentDao;
import com.coaching.entity.Student;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
	
	 private final StudentDao studentDao;

	    public List<Student> getAllStudents() {
	        return studentDao.findAll();
	    }	

	    public Student getStudent(Long id) {
	        return studentDao.findById(id)
	                .orElseThrow(() -> new RuntimeException("Student Not Found"));
	    }

	    public Student save(Student student) {
	        return studentDao.save(student);
	    }
	    
	    public Student update(Long id, Student student) {

	        Student s = getStudent(id);

	        s.setName(student.getName());
	        s.setEmail(student.getEmail());
	        s.setPhone(student.getPhone());
	        s.setAddress(student.getAddress());

	        return studentDao.save(s);
	    }

	    public void delete(Long id) {
	    	studentDao.deleteById(id);
	    }
}
