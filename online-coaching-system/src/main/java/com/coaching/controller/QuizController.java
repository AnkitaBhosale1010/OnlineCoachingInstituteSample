package com.coaching.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coaching.dto.QuizAnswerDto;
import com.coaching.entity.ApiResponse;
import com.coaching.entity.Quiz;
import com.coaching.entity.QuizAttempt;
import com.coaching.entity.QuizQuestion;
import com.coaching.service.QuestionService;
import com.coaching.service.QuizService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final QuestionService questionService;

    @PostMapping("/course/{courseId}")
    public ResponseEntity<Quiz> createQuiz(
            @PathVariable Long courseId,
            @RequestBody Quiz quiz) {

        return ResponseEntity.ok(
                quizService.createQuiz(courseId, quiz));
    }

    @GetMapping("/course/{courseId}")
    public List<Quiz> getCourseQuiz(
            @PathVariable Long courseId) {

        return quizService.getCourseQuiz(courseId);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuiz(
            @PathVariable Long quizId) {

        return ResponseEntity.ok(
                quizService.getQuiz(quizId));
    }

    @PostMapping("/{quizId}/questions")
    public ResponseEntity<QuizQuestion> addQuestion(
            @PathVariable Long quizId,
            @RequestBody QuizQuestion question) {

        return ResponseEntity.ok(
                quizService.addQuestion(quizId, question));
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<QuizQuestion>> getQuizQuestions(
            @PathVariable Long quizId) {

        return ResponseEntity.ok(
                quizService.getQuizQuestions(quizId));
    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity<QuizQuestion> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody QuizQuestion question) {

        return ResponseEntity.ok(
                quizService.updateQuestion(questionId, question));
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<String> deleteQuestion(
            @PathVariable Long questionId) {

        quizService.deleteQuestion(questionId);

        return ResponseEntity.ok(
                "Question Deleted Successfully");
    }

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<QuizAttempt>> submitAnswer(
            @Valid @RequestBody QuizAnswerDto dto) {

        QuizAttempt result =
                questionService.submitAnswer(dto);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Quiz Submitted Successfully",
                        result));
    }
    
    @PutMapping("/{quizId}")
    public ResponseEntity<Quiz> updateQuiz(
            @PathVariable Long quizId,
            @RequestBody Quiz quiz) {

        return ResponseEntity.ok(
                quizService.updateQuiz(quizId, quiz));
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<String> deleteQuiz(
            @PathVariable Long quizId) {

        quizService.deleteQuiz(quizId);

        return ResponseEntity.ok("Quiz Deleted Successfully");
    }

}