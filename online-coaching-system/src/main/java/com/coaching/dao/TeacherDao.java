package com.coaching.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.Teacher;

public interface TeacherDao extends JpaRepository<Teacher, Long>{
	
	 Optional<Teacher> findByEmail(String email);
}
