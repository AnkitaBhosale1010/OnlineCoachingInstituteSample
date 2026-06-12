package com.coaching.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.Enrollment;

public interface EnrollmentDao extends JpaRepository<Enrollment, Long>{

	List<Enrollment> findByStudentStudentId(Long studentId);

    List<Enrollment> findByCourseCourseId(Long courseId);
}
