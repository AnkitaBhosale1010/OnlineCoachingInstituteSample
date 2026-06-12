package com.coaching.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.Course;

public interface CourseDao extends JpaRepository<Course, Long>{
	
	List<Course> findByTitleContainingIgnoreCase(String title);

    List<Course> findByTeacherTeacherId(Long teacherId);
}
