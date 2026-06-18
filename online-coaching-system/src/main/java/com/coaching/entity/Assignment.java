package com.coaching.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assignment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long assignmentId;

	    private String title;

	    @Column(length = 3000)
	    private String description;

	    private LocalDate deadline;

	    private Integer totalMarks;

	    private LocalDate createdDate;

	    @ManyToOne
	    @JoinColumn(name = "course_id")
	    private Course course;
	}

