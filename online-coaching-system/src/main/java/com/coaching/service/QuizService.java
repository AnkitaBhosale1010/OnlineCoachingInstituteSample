package com.coaching.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coaching.dao.CourseDao;
import com.coaching.dao.QuizAttemptDao;
import com.coaching.dao.QuizDao;
import com.coaching.dao.QuizQuestionDao;
import com.coaching.dao.StudentDao;
import com.coaching.dto.QuizAnswerDto;
import com.coaching.entity.Course;
import com.coaching.entity.Quiz;
import com.coaching.entity.QuizAttempt;
import com.coaching.entity.QuizQuestion;
import com.coaching.entity.Student;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizService {

    private final StudentDao studentDao;
    private final QuizDao quizDao;
    private final CourseDao courseDao;
    private final QuizQuestionDao quizQuestionDao;
    private final QuizAttemptDao attemptDao;

    public Quiz createQuiz(Long courseId, Quiz quiz) {

        Course course = courseDao.findById(courseId)
                .orElseThrow();

        quiz.setCourse(course);

        return quizDao.save(quiz);
    }

    public QuizQuestion addQuestion(
            Long quizId,
            QuizQuestion question) {

        Quiz quiz = quizDao.findById(quizId)
                .orElseThrow();

        question.setQuiz(quiz);

        return quizQuestionDao.save(question);
    }

    public List<QuizQuestion> getQuizQuestions(
            Long quizId) {

        Quiz quiz = quizDao.findById(quizId)
                .orElseThrow(() ->
                        new RuntimeException("Quiz not found"));

        return quizQuestionDao.findByQuizQuizId(
                quiz.getQuizId());
    }

    public QuizQuestion updateQuestion(
            Long questionId,
            QuizQuestion updatedQuestion) {

        QuizQuestion question = quizQuestionDao
                .findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException("Question not found"));

        question.setQuestion(updatedQuestion.getQuestion());
        question.setOptionA(updatedQuestion.getOptionA());
        question.setOptionB(updatedQuestion.getOptionB());
        question.setOptionC(updatedQuestion.getOptionC());
        question.setOptionD(updatedQuestion.getOptionD());
        question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
        question.setMarks(updatedQuestion.getMarks());

        return quizQuestionDao.save(question);
    }

    public void deleteQuestion(Long questionId) {

        QuizQuestion question = quizQuestionDao
                .findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException("Question not found"));

        quizQuestionDao.delete(question);
    }

    public QuizAttempt submitAnswer(
            QuizAnswerDto dto) {

        QuizQuestion question = quizQuestionDao
                .findById(dto.getQuestionId())
                .orElseThrow();

        Student student = studentDao
                .findById(dto.getStudentId())
                .orElseThrow();

        QuizAttempt attempt = new QuizAttempt();

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

    public List<Quiz> getCourseQuiz(
            Long courseId) {

        return quizDao.findByCourseCourseId(
                courseId);
    }

    public Quiz getQuiz(Long quizId) {

        return quizDao.findById(quizId)
                .orElseThrow();
    }
    
    public Quiz updateQuiz(Long quizId, Quiz quiz) {

        Quiz existingQuiz = quizDao.findById(quizId).orElseThrow();

        existingQuiz.setTitle(quiz.getTitle());
        existingQuiz.setDescription(quiz.getDescription());
        existingQuiz.setTotalMarks(quiz.getTotalMarks());
        existingQuiz.setQuizDate(quiz.getQuizDate());

        return quizDao.save(existingQuiz);
    }

    public void deleteQuiz(Long quizId) {

        Quiz quiz = quizDao.findById(quizId)
                .orElseThrow(() ->
                        new RuntimeException("Quiz not found"));

        List<QuizQuestion> questions =
                quizQuestionDao.findByQuizQuizId(quizId);

        if(!questions.isEmpty()){
            quizQuestionDao.deleteAll(questions);
        }

        quizDao.delete(quiz);

    }

}