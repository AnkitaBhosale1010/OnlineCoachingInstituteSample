package com.coaching.service;

import java.time.LocalDate;
import java.util.List;

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

    public Submission submitAssignment(Long assignmentId,Long studentId,Submission submission) {

        Assignment assignment = assignmentDao.findById(assignmentId).orElseThrow();

        Student student = studentDao.findById(studentId).orElseThrow();

        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setSubmissionDate(LocalDate.now());

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

        Submission submission =submissionDao.findById(submissionId)
                        .orElseThrow();

        submission.setMarksObtained(marks);
        submission.setFeedback(feedback);

        return submissionDao.save(submission);
    }
}
