package com.coaching.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResult {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long resultId;

	    private Integer totalQuestions;

	    private Integer correctAnswers;

	    private Integer wrongAnswers;

	    private Integer totalMarks;

	    @ManyToOne
	    @JoinColumn(name = "quiz_id")
	    private Quiz quiz;

	    @ManyToOne
	    @JoinColumn(name = "student_id")
	    private Student student;
}
