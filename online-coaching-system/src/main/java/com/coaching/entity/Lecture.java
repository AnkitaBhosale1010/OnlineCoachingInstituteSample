package com.coaching.entity;

import java.time.LocalDate;
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
@Table(name = "lecture")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long lectureId;

	    private String title;

	    private String description;

	    private String videoUrl;

	    private Integer lectureOrder;

	    private LocalDate uploadDate;

	    @ManyToOne
	    @JoinColumn(name = "course_id")
	    private Course course;
	}

