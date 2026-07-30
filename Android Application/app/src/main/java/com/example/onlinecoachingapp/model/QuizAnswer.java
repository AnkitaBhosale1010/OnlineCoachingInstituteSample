package com.example.onlinecoachingapp.model;

public class QuizAnswer {
    private Long studentId;
    private Long questionId;
    private String answer;

    public QuizAnswer(Long studentId, Long questionId, String answer) {
        this.studentId = studentId;
        this.questionId = questionId;
        this.answer = answer;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
