package com.coaching.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = " course_id")
    private Long courseId;

    private String title;

    @Column(length = 2000)
    private String description;

    private String duration;

    private String level;

    private BigDecimal price;

    @Column(name = " created_date")
    private LocalDate createdDate;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    
    

}
