package com.coaching.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.Quiz;

public interface QuizDao  extends JpaRepository<Quiz, Long> {

    List<Quiz> findByCourseCourseId(Long courseId);

}
