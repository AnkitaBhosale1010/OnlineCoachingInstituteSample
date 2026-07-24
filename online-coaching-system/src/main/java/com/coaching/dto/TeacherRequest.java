package com.coaching.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TeacherRequest {

	@NotBlank
    @Size(min = 3, max = 50)
    private String name;
	
	@Email
	@NotBlank
    private String email;
	
	@Size(min = 6)
    private String password;

	@NotBlank
    private String expertise;
    private String qualification;
    
    @Pattern(regexp = "^[0-9]{10}$",message = "Enter valid mobile number")
    private String phone;
    private LocalDate joinDate;
}
