package com.coaching.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.coaching.dao.AssignmentDao;
import com.coaching.dao.AssignmentRequest;
import com.coaching.dao.CourseDao;
import com.coaching.entity.Assignment;
import com.coaching.entity.Course;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentService {
	
	private final AssignmentDao assignmentDao;
    private final CourseDao courseDao;

    public Assignment createAssignment(
            Long courseId,
            AssignmentRequest request) {


        Course course =
        		courseDao.findById(courseId)
                .orElseThrow(
                    () -> new RuntimeException("Course not found")
                );


        Assignment assignment = new Assignment();

        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        assignment.setDeadline(request.getDeadline());
        assignment.setTotalMarks(request.getMarks());
        assignment.setCourse(course);


        return assignmentDao.save(assignment);
    }
    public List<Assignment> getCourseAssignments(
            Long courseId) {

        return assignmentDao.findByCourseCourseId(courseId);
    }

    public Assignment getAssignment(Long id) {

        return assignmentDao.findById(id).orElseThrow(() ->
                        new RuntimeException("Assignment Not Found"));
    }

    public Assignment updateAssignment(
            Long id,
            Assignment assignment) {

        Assignment existing = getAssignment(id);

        existing.setTitle(assignment.getTitle());
        existing.setDescription(assignment.getDescription());
        existing.setDeadline(assignment.getDeadline());
        existing.setTotalMarks(assignment.getTotalMarks());

        return assignmentDao.save(existing);
    }

    public void deleteAssignment(Long id) {
        assignmentDao.deleteById(id);
    }
}
