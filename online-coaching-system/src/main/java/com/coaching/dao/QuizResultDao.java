package com.coaching.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.QuizResult;

public interface QuizResultDao extends JpaRepository<QuizResult, Long> {

	    List<QuizResult> findByStudentStudentId(Long studentId);

	    List<QuizResult> findByQuizQuizId(Long quizId);
}
