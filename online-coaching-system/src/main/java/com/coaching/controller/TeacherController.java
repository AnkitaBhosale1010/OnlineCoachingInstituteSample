package com.coaching.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coaching.dto.TeacherRequest;
import com.coaching.entity.ApiResponse;
import com.coaching.entity.Teacher;
import com.coaching.service.TeacherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TeacherController {
	
	 private final TeacherService teacherService;

	    @PostMapping
	    public ResponseEntity<ApiResponse<Teacher>> createTeacher(@Valid @RequestBody TeacherRequest request){

	        Teacher teacher = teacherService.createTeacher(request);

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(new ApiResponse<>(true,
	                                "Teacher Created Successfully",
	                                teacher));
	    }

	    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
	    @GetMapping
	    public List<Teacher> getAllTeachers(){

	        return teacherService.getAllTeachers();
	    }

	    @GetMapping("/{id}")
	    public Teacher getTeacherById(
	            @PathVariable Long id){

	        return teacherService.getTeacherById(id);
	    }
	    
	    @PutMapping("/{id}")
	    public Teacher updateTeacher(
	            @PathVariable Long id,
	            @RequestBody Teacher teacher) {

	        return teacherService.updateTeacher(
	                id,
	                teacher
	        );
	    }
	    
	    @GetMapping("/search")
	    public List<Teacher> searchTeacher(
	            @RequestParam String expertise) {

	        return teacherService.searchTeacher(expertise);
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse<String>> deleteTeacher(
	            @PathVariable Long id) {

	        teacherService.deleteTeacher(id);

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Teacher Deleted Successfully",
	                        null
	                )
	        );
	    }
}
