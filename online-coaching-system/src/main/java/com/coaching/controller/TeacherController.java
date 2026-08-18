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

import com.coaching.dto.CourseRequest;
import com.coaching.dto.LectureRequest;
import com.coaching.dto.TeacherRequest;
import com.coaching.entity.ApiResponse;
import com.coaching.entity.Course;
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
	    @GetMapping("/{teacherId}/courses")
	    public ResponseEntity<ApiResponse<List<Course>>> getTeacherCourses(
	            @PathVariable Long teacherId) {

	        List<Course> courses = teacherService.getTeacherCourses(teacherId);

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Teacher Courses",
	                        courses
	                )
	        );
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
	    
	    @GetMapping("/search")
	    public List<Teacher> searchTeacher(
	            @RequestParam String expertise) {

	        return teacherService.searchTeacher(expertise);
	    }
	    
	    @PostMapping("/lecture")
	    public ResponseEntity<ApiResponse<String>> uploadLecture(
	            @RequestBody LectureRequest request){

	        teacherService.uploadLecture(request);

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Lecture Uploaded Successfully",
	                        null
	                ));
	    }
}
