package com.coaching.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.coaching.entity.Student;

public interface StudentDao extends JpaRepository<Student, Long>{
	
	 List<Student> findByBatchId(Long batchId);
	 
	 Optional<Student> findByUserUserId(Long userId);

}
