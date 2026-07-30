package com.coaching.dto;

import lombok.Data;

@Data
public class StudentResponseDto {

	private Long studentId;

    private String name;

    private String email;

    private String phone;

    private String batchName;
}
