package com.coaching.service;

import org.springframework.stereotype.Service;
import com.coaching.dao.QuizAttemptDao;
import com.coaching.dao.QuizDao;
import com.coaching.dao.QuizQuestionDao;
import com.coaching.dao.StudentDao;
import com.coaching.dto.QuizAnswerDto;
import com.coaching.entity.Quiz;
import com.coaching.entity.QuizAttempt;
import com.coaching.entity.QuizQuestion;
import com.coaching.entity.Student;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuizDao quizDao;
    private final QuizQuestionDao questionDao;
    private final QuizAttemptDao attemptDao;
    private final StudentDao studentDao;

    // Add Question to Quiz
    public QuizQuestion addQuestion(Long quizId,QuizQuestion question) {

        Quiz quiz = quizDao
                .findById(quizId)
                .orElseThrow(() ->
                        new RuntimeException("Quiz Not Found"));

        question.setQuiz(quiz);

        return questionDao.save(question);
    }

    // Student Submit Answer
    public QuizAttempt submitAnswer(QuizAnswerDto dto) {

        QuizQuestion question =
                questionDao.findById(
                                dto.getQuestionId())
                        .orElseThrow(() ->
                                new RuntimeException("Question Not Found"));

        Student student =
                studentDao.findById(
                                dto.getStudentId())
                        .orElseThrow(() ->
                                new RuntimeException("Student Not Found"));

        QuizAttempt attempt =
                new QuizAttempt();

        attempt.setQuestion(question);
        attempt.setStudent(student);
        attempt.setSelectedAnswer(dto.getAnswer());

        if (question.getCorrectAnswer()
                .equalsIgnoreCase(dto.getAnswer())) {

            attempt.setObtainedMarks(
                    question.getMarks());

        } else {

            attempt.setObtainedMarks(0);
        }

        return attemptDao.save(attempt);
    }
}
