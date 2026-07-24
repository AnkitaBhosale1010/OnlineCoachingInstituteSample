package com.coaching.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizAnswerDto {

	@NotNull
    private Long questionId;

	@NotNull
    private Long studentId;

	@NotBlank(message = "Answer cannot be empty")
    private String answer;
}
