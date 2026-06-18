package com.coaching.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.QuizAttempt;

public interface QuizAttemptDao extends JpaRepository<QuizAttempt, Long> {

    List<QuizAttempt> findByStudentStudentId(Long studentId);

}
