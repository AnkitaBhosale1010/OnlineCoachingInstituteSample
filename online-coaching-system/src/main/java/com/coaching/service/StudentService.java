package com.coaching.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coaching.dao.AssignmentDao;
import com.coaching.dao.EnrollmentDao;
import com.coaching.dao.LectureDao;
import com.coaching.dao.QuizDao;
import com.coaching.dao.StudentDao;
import com.coaching.dao.UserDao;
import com.coaching.dto.DashboardResponse;
import com.coaching.dto.StudentRequest;
import com.coaching.entity.Enrollment;
import com.coaching.entity.Student;
import com.coaching.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
	
	 private final StudentDao studentDao;
	 private final UserDao userDao;
	 private final PasswordEncoder passwordEncoder;
	 private final EnrollmentDao enrollmentDao;
	 private final LectureDao lectureDao;
	 private final AssignmentDao assignmentDao;
	 private final QuizDao quizDao;

	 public DashboardResponse getDashboard(Long studentId) {

	        DashboardResponse response = new DashboardResponse();

	        List<Enrollment> enrollments =
	                enrollmentDao.findByStudentStudentId(studentId);

	        int lectureCount = 0;
	        int assignmentCount = 0;
	        int quizCount = 0;

	        for (Enrollment enrollment : enrollments) {

	            Long courseId = enrollment.getCourse().getCourseId();

	            lectureCount += lectureDao
	                    .findByCourseCourseId(courseId)
	                    .size();

	            assignmentCount += assignmentDao
	                    .findByCourseCourseId(courseId)
	                    .size();

	            quizCount += quizDao
	                    .findByCourseCourseId(courseId)
	                    .size();
	        }

	        response.setTotalCourses(enrollments.size());
	        response.setTotalLectures(lectureCount);
	        response.setPendingAssignments(assignmentCount);
	        response.setUpcomingQuizzes(quizCount);

	        return response;
	    }
	 
	 public Student createStudent(
	            StudentRequest request){

	        User user = new User();

	        user.setName(request.getName());
	        user.setEmail(request.getEmail());
	        user.setPassword(passwordEncoder.encode(request.getPassword()));
	        user.setRole("STUDENT");

	        user = userDao.save(user);

	        Student student = new Student();

	        student.setUser(user);
	        student.setAddress(request.getAddress());
	        student.setDob(request.getDob());
	        student.setPhone(request.getPhone());
	        student.setJoinDate(request.getJoinDate());

	        return studentDao.save(student);
	    }

	    public List<Student> getAllStudents(){
	        return studentDao.findAll();
	    }

	    public Student getStudentById(Long id){

	        return studentDao.findById(id)
	                .orElseThrow(() ->
	                new RuntimeException("Student Not Found"));
	    }
	    
	    public Student getStudentByEmail(String email) {

	        User user = userDao.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("User Not Found"));

	        return studentDao.findByUserUserId(user.getUserId())
	                .orElseThrow(() -> new RuntimeException("Student Not Found"));
	    }
	    
	    public void deleteStudent(Long studentId) {

	        Student student = studentDao.findById(studentId)
	                .orElseThrow(() ->
	                        new RuntimeException("Student Not Found"));

	        userDao.delete(student.getUser());

	        studentDao.delete(student);
	    }
}
