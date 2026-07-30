package com.coaching.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "batches")
public class Batch {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String batchName;

	    private String trainerName;

	    private String startDate;

	    private String endDate;

	    private String status;
	    
	    @JsonIgnore
	    @OneToMany(mappedBy = "batch",
	            cascade = CascadeType.ALL,
	            fetch = FetchType.LAZY)
	    private List<Student> students = new ArrayList<>();
}
