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
@Table(name = "study_material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyMaterial {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialId;

    private String title;

    private String fileUrl;

    private LocalDate uploadDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
