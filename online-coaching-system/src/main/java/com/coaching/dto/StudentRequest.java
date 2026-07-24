package com.coaching.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentRequest {

	@NotBlank(message = "Student name is required")
    @Size(min = 3, max = 50)
    private String name;
	
	@Email(message = "Invalid Email")
    @NotBlank
    private String email;
	
	@NotBlank
    @Size(min = 6)
    private String password;

	@NotBlank
    private String address;
    private LocalDate dob;
    
    @Pattern(regexp = "^[0-9]{10}$",message = "Mobile number must contain exactly 10 digits")
    private String phone;
    private LocalDate joinDate;
}
