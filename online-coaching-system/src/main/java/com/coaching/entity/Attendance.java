package com.coaching.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long attendanceId;

	    private LocalDate attendanceDate;

	    private String status;      // PRESENT / ABSENT

	    @ManyToOne
	    @JoinColumn(name = "student_id")
	    private Student student;

	    @ManyToOne
	    @JoinColumn(name = "course_id")
	    private Course course;
}
