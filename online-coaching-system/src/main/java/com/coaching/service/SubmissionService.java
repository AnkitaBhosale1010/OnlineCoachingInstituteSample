package com.coaching.service;

import java.time.LocalDateTime;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import com.coaching.dao.AssignmentDao;
import com.coaching.dao.StudentDao;
import com.coaching.dao.SubmissionDao;
import com.coaching.entity.Assignment;
import com.coaching.entity.Student;
import com.coaching.entity.Submission;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionDao submissionDao;
    private final AssignmentDao assignmentDao;
    private final StudentDao studentDao;

    public Submission submitAssignment(
            Long assignmentId,
            Long studentId,
            MultipartFile file,
            String remarks) throws IOException {

    	Assignment assignment = assignmentDao.findById(assignmentId)
    	        .orElseThrow(() ->
    	                new RuntimeException("Assignment Not Found"));

    	Student student = studentDao.findById(studentId)
    	        .orElseThrow(() ->
    	                new RuntimeException("Student Not Found"));

        String uploadDir = "uploads/submissions/";

        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis()
                + "_"
                + file.getOriginalFilename();

        Path path = Paths.get(uploadDir, fileName);

        Files.copy(file.getInputStream(), path);

        Submission submission =
                submissionDao.findByAssignmentAssignmentIdAndStudentStudentId(
                        assignmentId,
                        studentId
                );

        if (submission == null) {

            submission = new Submission();

            submission.setAssignment(assignment);

            submission.setStudent(student);

        }

        submission.setSubmittedAt(LocalDateTime.now());

        submission.setFileName(fileName);

        submission.setFilePath(path.toString());

        submission.setRemarks(remarks);

        return submissionDao.save(submission);
        
    }
    
    public List<Submission> getAssignmentSubmissions(
            Long assignmentId) {

        return submissionDao.findByAssignmentAssignmentId(assignmentId);
    }

    public List<Submission> getStudentSubmissions(
            Long studentId) {

        return submissionDao.findByStudentStudentId(studentId);
    }

    public Submission giveMarks(
            Long submissionId,
            Integer marks,
            String feedback) {

        Submission submission = submissionDao.findById(submissionId)
                .orElseThrow();

        submission.setMarks(Double.valueOf(marks));
        submission.setFeedback(feedback);

        return submissionDao.save(submission);
    }


}
