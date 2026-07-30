package com.coaching.dto;

import lombok.Data;

@Data
public class AssignStudentBatchRequest {

	private Long studentId;
    private Long batchId;
}
