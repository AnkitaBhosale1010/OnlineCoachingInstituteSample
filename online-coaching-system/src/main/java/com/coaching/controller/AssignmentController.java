package com.coaching.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coaching.entity.Assignment;
import com.coaching.service.AssignmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

	private final AssignmentService assignmentService;

    @PostMapping("/course/{courseId}")
    public Assignment createAssignment(@PathVariable Long courseId,@RequestBody Assignment assignment) {

        return assignmentService.createAssignment(courseId, assignment);
    }

    @GetMapping("/course/{courseId}")
    public List<Assignment> getAssignments(@PathVariable Long courseId) {

        return assignmentService.getCourseAssignments(courseId);
    }

    @GetMapping("/{id}")
    public Assignment getAssignment(@PathVariable Long id) {

        return assignmentService.getAssignment(id);
    }

    @PutMapping("/{id}")
    public Assignment updateAssignment(@PathVariable Long id,@RequestBody Assignment assignment) {

        return assignmentService.updateAssignment(id, assignment);
    }

    @DeleteMapping("/{id}")
    public String deleteAssignment(@PathVariable Long id) {

        assignmentService.deleteAssignment(id);

        return "Assignment Deleted";
    }
}
