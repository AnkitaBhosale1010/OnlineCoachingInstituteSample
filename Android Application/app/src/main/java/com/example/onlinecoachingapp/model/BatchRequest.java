package com.example.onlinecoachingapp.model;

public class BatchRequest {

    private String batchName;
    private String trainerName;
    private String startDate;
    private String endDate;
    private String status;

    public BatchRequest() {
    }

    public BatchRequest(
            String batchName,
            String trainerName,
            String startDate,
            String endDate,
            String status) {

        this.batchName = batchName;
        this.trainerName = trainerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
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
}