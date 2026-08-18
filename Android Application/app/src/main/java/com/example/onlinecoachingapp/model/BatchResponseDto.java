package com.example.onlinecoachingapp.model;

import java.util.List;

public class BatchResponseDto {

    private Long id;

    private String batchName;

    private String trainerName;

    private String startDate;

    private String endDate;

    private String status;

    private List<Student> students;


    public BatchResponseDto() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getBatchName() {
        return batchName;
    }


    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }


    public String getTrainerName() {
        return trainerName;
    }


    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }


    public String getStartDate() {
        return startDate;
    }


    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getEndDate() {
        return endDate;
    }


    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public List<Student> getStudents() {
        return students;
    }


    public void setStudents(List<Student> students) {
        this.students = students;
    }

}