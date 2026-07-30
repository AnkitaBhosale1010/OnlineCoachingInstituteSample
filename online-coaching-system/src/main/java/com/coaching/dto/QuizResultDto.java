package com.coaching.dto;

import lombok.Data;

@Data
public class QuizResultDto {

	 	private Long quizId;

	    private Long studentId;

	    private Integer totalQuestions;

	    private Integer correctAnswers;

	    private Integer wrongAnswers;

	    private Integer totalMarks;
}
