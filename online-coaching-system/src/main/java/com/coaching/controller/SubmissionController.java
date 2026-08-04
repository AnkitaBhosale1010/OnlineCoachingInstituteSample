package com.coaching.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.coaching.dto.MarksDto;
import com.coaching.entity.Submission;
import com.coaching.service.SubmissionService;
import java.io.IOException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {
	
	private final SubmissionService submissionService;

	@PreAuthorize("hasRole('STUDENT')")
	@PostMapping(value = "/assignment/{assignmentId}/student/{studentId}",
	        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Submission submitAssignment(

	        @PathVariable Long assignmentId,

	        @PathVariable Long studentId,

	        @RequestParam("file") MultipartFile file,

	        @RequestParam(value = "remarks", required = false)
	        String remarks) throws IOException {

	    return submissionService.submitAssignment(
	            assignmentId,
	            studentId,
	            file,
	            remarks);
	}

    @GetMapping("/assignment/{assignmentId}")
    public List<Submission> getAssignmentSubmissions(@PathVariable Long assignmentId) {

        return submissionService.getAssignmentSubmissions(assignmentId);
    }

    @GetMapping("/student/{studentId}")
    public List<Submission> getStudentSubmissions(@PathVariable Long studentId) {

        return submissionService.getStudentSubmissions(studentId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @PutMapping("/{submissionId}/marks")
    public Submission giveMarks(@PathVariable Long submissionId,@Valid @RequestBody MarksDto dto) {

        return submissionService.giveMarks(
                submissionId,
                dto.getMarks(),
                dto.getFeedback());
    }

}
