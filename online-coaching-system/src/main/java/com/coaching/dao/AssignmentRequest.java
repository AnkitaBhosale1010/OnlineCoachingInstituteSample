package com.coaching.dao;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AssignmentRequest {
	
	    private String title;

	    private String description;

	    private LocalDate deadline;

	    private Integer marks;
}
