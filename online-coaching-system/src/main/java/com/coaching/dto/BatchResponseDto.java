package com.coaching.dto;

import java.util.List;
import lombok.Data;

@Data
public class BatchResponseDto {

	private Long id;

    private String batchName;

    private String trainerName;

    private String startDate;

    private String endDate;

    private String status;

    private List<StudentResponseDto> students;
}
