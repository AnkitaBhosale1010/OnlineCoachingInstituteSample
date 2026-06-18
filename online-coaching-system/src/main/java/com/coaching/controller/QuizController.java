package com.coaching.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coaching.dto.QuizAnswerDto;
import com.coaching.entity.Quiz;
import com.coaching.entity.QuizAttempt;
import com.coaching.entity.QuizQuestion;
import com.coaching.service.QuestionService;
import com.coaching.service.QuizService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {
	 private final QuizService quizService;
	    private final QuestionService questionService;

	    @PostMapping("/course/{courseId}")
	    public Quiz createQuiz(
	            @PathVariable Long courseId,
	            @RequestBody Quiz quiz) {

	        return quizService.createQuiz(courseId, quiz);
	    }

	    @GetMapping("/course/{courseId}")
	    public List<Quiz> getCourseQuiz(
	            @PathVariable Long courseId) {

	        return quizService.getCourseQuiz(courseId);
	    }

	    @GetMapping("/{id}")
	    public Quiz getQuiz(
	            @PathVariable Long id) {

	        return quizService.getQuiz(id);
	    }

	    @PostMapping("/{quizId}/questions")
	    public QuizQuestion addQuestion(
	            @PathVariable Long quizId,
	            @RequestBody QuizQuestion question) {

	        return questionService.addQuestion(
	                quizId,
	                question);
	    }

	    @PostMapping("/submit")
	    public QuizAttempt submitAnswer(
	            @RequestBody QuizAnswerDto dto) {

	        return questionService.submitAnswer(dto);
	    }
}
