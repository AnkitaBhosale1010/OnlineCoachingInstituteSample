package com.coaching.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.QuizQuestion;

public interface QuizQuestionDao extends JpaRepository<QuizQuestion, Long> {

    List<QuizQuestion> findByQuizQuizId(Long quizId);

}
