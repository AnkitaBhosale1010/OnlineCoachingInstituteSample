package com.coaching.service;

import java.util.List;
import com.coaching.dto.BatchDto;
import com.coaching.dto.BatchResponseDto;
import com.coaching.entity.Student;

public interface BatchService {
	
	    BatchResponseDto createBatch(BatchDto dto);

	    List<BatchResponseDto> getAllBatches();

	    BatchResponseDto getBatchById(Long id);

	    BatchResponseDto updateBatch(Long id, BatchDto dto);

	    void deleteBatch(Long id);

	    Student assignStudent(Long studentId, Long batchId);

	    List<Student> getStudentsByBatch(Long batchId);
	
}
