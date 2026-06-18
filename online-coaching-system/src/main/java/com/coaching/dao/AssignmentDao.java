package com.coaching.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.Assignment;

public interface AssignmentDao extends JpaRepository<Assignment, Long> {

	List<Assignment> findByCourseCourseId(Long courseId);
}
