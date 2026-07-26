package com.coaching.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	@NotBlank(message = "Name is required")
    @Size(min = 3, max = 50,message = "Name must contain 3-50 characters")
    private String name;
	
	@Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 6,message = "Password must contain at least 6 characters")
    private String password;
	
	@NotBlank(message = "Role is required")
    @Pattern(regexp = "ADMIN|TEACHER|STUDENT",message = "Role must be ADMIN, TEACHER or STUDENT")
    private String role;
	
}