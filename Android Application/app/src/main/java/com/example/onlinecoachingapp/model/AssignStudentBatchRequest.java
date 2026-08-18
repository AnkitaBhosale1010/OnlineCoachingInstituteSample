package com.example.onlinecoachingapp.model;

public class AssignStudentBatchRequest {

    private Long studentId;
    private Long batchId;

    public AssignStudentBatchRequest() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }
}