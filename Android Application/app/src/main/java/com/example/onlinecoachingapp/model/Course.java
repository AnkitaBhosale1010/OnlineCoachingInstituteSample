package com.example.onlinecoachingapp.model;

public class Course {

    private Long id;
    private String courseName;
    private String description;
    private double fees;
    private int duration;

    public Course() {
    }

    public Long getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDescription() {
        return description;
    }

    public double getFees() {
        return fees;
    }

    public int getDuration() {
        return duration;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
