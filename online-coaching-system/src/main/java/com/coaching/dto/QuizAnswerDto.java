package com.coaching.dto;

import lombok.Data;

@Data
public class QuizAnswerDto {

    private Long questionId;

    private Long studentId;

    private String answer;
}
