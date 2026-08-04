package com.example.onlinecoachingapp.model;

public class DashboardResponse {

    private int totalCourses;
    private int totalLectures;
    private int pendingAssignments;
    private int upcomingQuizzes;

    public DashboardResponse() {
    }

    public int getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(int totalCourses) {
        this.totalCourses = totalCourses;
    }

    public int getTotalLectures() {
        return totalLectures;
    }

    public void setTotalLectures(int totalLectures) {
        this.totalLectures = totalLectures;
    }

    public int getPendingAssignments() {
        return pendingAssignments;
    }

    public void setPendingAssignments(int pendingAssignments) {
        this.pendingAssignments = pendingAssignments;
    }

    public int getUpcomingQuizzes() {
        return upcomingQuizzes;
    }

    public void setUpcomingQuizzes(int upcomingQuizzes) {
        this.upcomingQuizzes = upcomingQuizzes;
    }


}
