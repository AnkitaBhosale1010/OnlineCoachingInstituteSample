package com.coaching.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.Lecture;

public interface LectureDao extends JpaRepository<Lecture, Long>  {
 
	List<Lecture> findByCourseCourseId(Long courseId);
}
