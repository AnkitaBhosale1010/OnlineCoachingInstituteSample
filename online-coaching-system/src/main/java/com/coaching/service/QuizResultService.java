package com.coaching.service;

import org.springframework.stereotype.Service;
import com.coaching.dao.QuizDao;
import com.coaching.dao.QuizResultDao;
import com.coaching.dao.StudentDao;
import com.coaching.dto.QuizResultDto;
import com.coaching.entity.Quiz;
import com.coaching.entity.QuizResult;
import com.coaching.entity.Student;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizResultService {

	private final QuizResultDao resultDao;
    private final QuizDao quizDao;
    private final StudentDao studentDao;

    public QuizResult saveResult(QuizResultDto dto){

        Quiz quiz = quizDao.findById(dto.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz Not Found"));

        Student student = studentDao.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        QuizResult result = new QuizResult();

        result.setQuiz(quiz);
        result.setStudent(student);
        result.setTotalQuestions(dto.getTotalQuestions());
        result.setCorrectAnswers(dto.getCorrectAnswers());
        result.setWrongAnswers(dto.getWrongAnswers());
        result.setTotalMarks(dto.getTotalMarks());

        return resultDao.save(result);
    }
}
