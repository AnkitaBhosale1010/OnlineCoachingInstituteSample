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

import com.coaching.entity.ApiResponse;
import com.coaching.entity.Course;
import com.coaching.service.CourseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CourseController {
	
	 private final CourseService courseService;

	    @GetMapping
	    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses() {
	    	 return ResponseEntity.ok(
	    	            new ApiResponse<>(
	    	                    true,
	    	                    "Course List",
	    	                    courseService.getAllCourses()));
	    }

	    @GetMapping("/{id}")
	    public Course getCourse(
	            @PathVariable Long id) {

	        return courseService.getCourse(id);
	    }

	    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
	    @PostMapping("/teacher/{teacherId}")
	    public ResponseEntity<ApiResponse<Course>> addCourse(
	            @PathVariable Long teacherId,
	            @RequestBody Course course) {

	    	Course saved = courseService.createCourse(course,teacherId);

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(new ApiResponse<>(
	                                true,
	                                "Course Created Successfully",
	                                saved));
	    }

	    @PutMapping("/{id}")
	    public Course updateCourse(
	            @PathVariable Long id,
	            @RequestBody Course course) {

	        return courseService
	                .updateCourse(id, course);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse<Void>> deleteCourse(
	            @PathVariable Long id) {

	        courseService.deleteCourse(id);

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Course Deleted Successfully",
	                        null));
	    }

	    @GetMapping("/search")
	    public List<Course> searchCourse(
	            @RequestParam String title) {

	        return courseService.searchCourse(title);
	    }

	    @GetMapping("/teacher/{teacherId}")
	    public List<Course> getTeacherCourses(
	            @PathVariable Long teacherId) {

	        return courseService
	                .getTeacherCourses(teacherId);
	    }
}
