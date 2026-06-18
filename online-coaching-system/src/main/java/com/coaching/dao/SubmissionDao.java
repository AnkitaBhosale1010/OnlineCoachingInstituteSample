package com.coaching.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.Submission;

public interface SubmissionDao extends JpaRepository<Submission, Long> {

    List<Submission> findByAssignmentAssignmentId(Long assignmentId);

    List<Submission> findByStudentStudentId(Long studentId);
}
