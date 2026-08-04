package com.coaching.dto;

import lombok.Data;

@Data
public class DashboardResponse {

	    private int totalCourses;
	    private int totalLectures;
	    private int pendingAssignments;
	    private int upcomingQuizzes;
}
