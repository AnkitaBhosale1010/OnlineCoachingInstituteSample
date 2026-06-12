package com.coaching.controller;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coaching.entity.Enrollment;
import com.coaching.service.EnrollmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EnrollmentController {
	
	private final EnrollmentService enrollmentService;

    @PostMapping("/student/{studentId}/course/{courseId}")
    public Enrollment enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        return enrollmentService.enrollStudent(studentId, courseId);
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> getStudentEnrollments(
            @PathVariable Long studentId) {

        return enrollmentService
                .getStudentEnrollments(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getCourseStudents(
            @PathVariable Long courseId) {

        return enrollmentService
                .getCourseStudents(courseId);
    }

    @PutMapping("/cancel/{enrollId}")
    public String cancelEnrollment(
            @PathVariable Long enrollId) {

        enrollmentService.cancelEnrollment(enrollId);

        return "Enrollment Cancelled";
    }
}
