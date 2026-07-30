package com.coaching.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coaching.dto.AssignStudentBatchRequest;
import com.coaching.dto.BatchDto;
import com.coaching.dto.BatchResponseDto;
import com.coaching.entity.Student;
import com.coaching.service.BatchService;

@RestController
@RequestMapping("/api/batches")
@CrossOrigin("*")
public class BatchController {

	 private final BatchService batchService;

	    public BatchController(BatchService batchService) {
	        this.batchService = batchService;
	    }

	    // Create Batch
	    @PostMapping
	    public ResponseEntity<BatchResponseDto> createBatch(
	            @RequestBody BatchDto dto) {

	        return new ResponseEntity<>(
	                batchService.createBatch(dto),
	                HttpStatus.CREATED);
	    }

	    // Get All Batches
	    @GetMapping
	    public ResponseEntity<List<BatchResponseDto>> getAllBatches() {

	        return ResponseEntity.ok(
	                batchService.getAllBatches());
	    }

	    // Get Batch By Id
	    @GetMapping("/{id}")
	    public ResponseEntity<BatchResponseDto> getBatchById(
	            @PathVariable Long id) {

	        return ResponseEntity.ok(
	                batchService.getBatchById(id));
	    }

	    // Update Batch
	    @PutMapping("/{id}")
	    public ResponseEntity<BatchResponseDto> updateBatch(
	            @PathVariable Long id,
	            @RequestBody BatchDto dto) {

	        return ResponseEntity.ok(
	                batchService.updateBatch(id, dto));
	    }

	    // Delete Batch
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteBatch(
	            @PathVariable Long id) {

	        batchService.deleteBatch(id);

	        return ResponseEntity.ok("Batch Deleted Successfully");
	    }

	    // Assign Student To Batch
	    @PostMapping("/assign")
	    public ResponseEntity<Student> assignStudent(
	            @RequestBody AssignStudentBatchRequest request) {

	        return ResponseEntity.ok(
	                batchService.assignStudent(
	                        request.getStudentId(),
	                        request.getBatchId()));
	    }

	    // Get Students By Batch
	    @GetMapping("/{batchId}/students")
	    public ResponseEntity<List<Student>> getStudents(
	            @PathVariable Long batchId) {

	        return ResponseEntity.ok(
	                batchService.getStudentsByBatch(batchId));
	    }

}
