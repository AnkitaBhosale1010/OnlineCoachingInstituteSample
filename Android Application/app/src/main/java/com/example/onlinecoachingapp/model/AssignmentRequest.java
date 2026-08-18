package com.example.onlinecoachingapp.model;

public class AssignmentRequest {

    private String title;

    private String description;

    private String deadline;

    private Integer totalMarks;

    public AssignmentRequest(String title, String description, String deadline) {
    }


    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public String getDeadline() {
        return deadline;
    }


    public Integer getTotalMarks() {
        return totalMarks;
    }
}
