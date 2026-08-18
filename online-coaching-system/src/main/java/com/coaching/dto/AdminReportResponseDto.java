package com.coaching.dto;

public class AdminReportResponseDto {

    private long totalStudents;
    private long totalTeachers;
    private long totalCourses;
    private long totalBatches;
    private long totalEnrollments;
    private long totalQuizResults;
    private long totalSubmissions;

    public AdminReportResponseDto() {
    }

    public AdminReportResponseDto(
            long totalStudents,
            long totalTeachers,
            long totalCourses,
            long totalBatches,
            long totalEnrollments,
            long totalQuizResults,
            long totalSubmissions) {

        this.totalStudents = totalStudents;
        this.totalTeachers = totalTeachers;
        this.totalCourses = totalCourses;
        this.totalBatches = totalBatches;
        this.totalEnrollments = totalEnrollments;
        this.totalQuizResults = totalQuizResults;
        this.totalSubmissions = totalSubmissions;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalTeachers() {
        return totalTeachers;
    }

    public void setTotalTeachers(long totalTeachers) {
        this.totalTeachers = totalTeachers;
    }

    public long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public long getTotalBatches() {
        return totalBatches;
    }

    public void setTotalBatches(long totalBatches) {
        this.totalBatches = totalBatches;
    }

    public long getTotalEnrollments() {
        return totalEnrollments;
    }

    public void setTotalEnrollments(long totalEnrollments) {
        this.totalEnrollments = totalEnrollments;
    }

    public long getTotalQuizResults() {
        return totalQuizResults;
    }

    public void setTotalQuizResults(long totalQuizResults) {
        this.totalQuizResults = totalQuizResults;
    }

    public long getTotalSubmissions() {
        return totalSubmissions;
    }

    public void setTotalSubmissions(long totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
    }
}