package com.coaching.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coaching.dao.CourseDao;
import com.coaching.dao.EnrollmentDao;
import com.coaching.dao.StudentDao;
import com.coaching.entity.Course;
import com.coaching.entity.Enrollment;
import com.coaching.entity.Student;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollmentService {
 
	private final EnrollmentDao enrollmentDao;
    private final StudentDao studentDao;
    private final CourseDao courseDao;

    public Enrollment enrollStudent(Long studentId,Long courseId) {

        Student student = studentDao.findById(studentId)
        		.orElseThrow(() -> new RuntimeException("Student Not Found"));

        Course course = courseDao.findById(courseId)
        		.orElseThrow(() -> new RuntimeException("Course Not Found"));
        
        // Check if already enrolled
        if (enrollmentDao
                .findByStudentStudentIdAndCourseCourseId(studentId, courseId)
                .isPresent()) {

            throw new RuntimeException("Student already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollDate(LocalDate.now());
        enrollment.setStatus("ACTIVE");

        return enrollmentDao.save(enrollment);
    }
    

    public List<Enrollment> getAllEnrollments() {
        return enrollmentDao.findAll();
    }

    public List<Enrollment> getStudentEnrollments(Long studentId) {

        return enrollmentDao.findByStudentStudentId(studentId);
    }

    public List<Enrollment> getCourseStudents(Long courseId) {

        return enrollmentDao.findByCourseCourseId(courseId);
    }

    public void cancelEnrollment(Long enrollId) {

        Enrollment enrollment =enrollmentDao.findById(enrollId).orElseThrow(() ->
                                new RuntimeException("Enrollment Not Found"));

        enrollment.setStatus("CANCELLED");

        enrollmentDao.save(enrollment);
    }
    
}
