package com.coaching.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coaching.dto.MarksDto;
import com.coaching.entity.Submission;
import com.coaching.service.SubmissionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {
	
	private final SubmissionService submissionService;

    @PostMapping("/assignment/{assignmentId}/student/{studentId}")
    public Submission submitAssignment(
            @PathVariable Long assignmentId,
            @PathVariable Long studentId,
            @RequestBody Submission submission) {

        return submissionService.submitAssignment(assignmentId,studentId,submission);
    }

    @GetMapping("/assignment/{assignmentId}")
    public List<Submission> getAssignmentSubmissions(@PathVariable Long assignmentId) {

        return submissionService.getAssignmentSubmissions(assignmentId);
    }

    @GetMapping("/student/{studentId}")
    public List<Submission> getStudentSubmissions(@PathVariable Long studentId) {

        return submissionService.getStudentSubmissions(studentId);
    }

    @PutMapping("/{submissionId}/marks")
    public Submission giveMarks(@PathVariable Long submissionId,@RequestBody MarksDto dto) {

        return submissionService.giveMarks(
                submissionId,
                dto.getMarks(),
                dto.getFeedback());
    }

}
