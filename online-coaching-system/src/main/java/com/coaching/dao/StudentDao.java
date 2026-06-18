package com.coaching.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.coaching.entity.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Long>{
	
	Optional<Student> findByEmail(String email);

}
