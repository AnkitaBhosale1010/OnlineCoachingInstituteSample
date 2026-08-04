package com.coaching.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coaching.dto.DashboardResponse;
import com.coaching.dto.StudentRequest;
import com.coaching.entity.ApiResponse;
import com.coaching.entity.Student;
import com.coaching.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
	
	private final StudentService studentService;
	
	 @GetMapping("/dashboard/{studentId}")
	    public DashboardResponse getDashboard(
	            @PathVariable Long studentId) {

	        return studentService.getDashboard(studentId);
	    }

	@PostMapping
	public ResponseEntity<ApiResponse<Student>> createStudent(@Valid @RequestBody StudentRequest request){

	    Student student = studentService.createStudent(request);

	    return ResponseEntity.status(HttpStatus.CREATED)

	            .body(new ApiResponse<>(true,"Student Created Successfully",student));

	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<ApiResponse<List<Student>>> getAllStudents(){

	    return ResponseEntity.ok(

	            new ApiResponse<>(true,"Student List",studentService.getAllStudents()));

	}
	
	@PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable Long id){

	    Student student = studentService.getStudentById(id);

	    return ResponseEntity.ok(

	            new ApiResponse<>(true,"Student Found",student));

	}
	@GetMapping("/profile")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<ApiResponse<Student>> getProfile(Authentication authentication) {

	    String email = authentication.getName();

	    Student student = studentService.getStudentByEmail(email);

	    return ResponseEntity.ok(
	            new ApiResponse<>(true, "Profile Found", student)
	    );
	}
}
