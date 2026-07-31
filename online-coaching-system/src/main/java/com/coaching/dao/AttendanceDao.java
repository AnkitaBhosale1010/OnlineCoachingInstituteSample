package com.coaching.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.Attendance;

public interface AttendanceDao extends JpaRepository<Attendance, Long>{

	List<Attendance> findByStudentStudentId(Long studentId);

    List<Attendance> findByCourseCourseId(Long courseId);
}
