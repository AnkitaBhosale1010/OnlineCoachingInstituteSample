package com.coaching.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

	private Long userId;
	
	private Long studentId;
	
	private Long teacherId;
	
    private String token;
    
    private String name;

    private String email;

    private String role;
}
