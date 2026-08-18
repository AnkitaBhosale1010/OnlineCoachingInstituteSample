package com.coaching.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.coaching.dao.CourseDao;
import com.coaching.dao.TeacherDao;
import com.coaching.dto.CourseRequest;
import com.coaching.entity.Course;
import com.coaching.entity.Teacher;
import com.coaching.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {
	
	 private final CourseDao courseRepository;
	    private final TeacherDao teacherDao;

	    public List<Course> getAllCourses() {
	        return courseRepository.findAll();
	    }

	    public Course getCourse(Long id) {

	        return courseRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Course Not Found"));
	    }


	    public Course createCourse(
	            CourseRequest request,
	            Long teacherId) {

	        Teacher teacher = teacherDao.findById(teacherId)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Teacher Not Found"));

	        Course course = new Course();

	        course.setTitle(request.getTitle());
	        course.setDescription(request.getDescription());
	        course.setDuration(request.getDuration());
	        course.setLevel(request.getLevel());
	        course.setPrice(request.getPrice());
	        course.setTeacher(teacher);
	        course.setCreatedDate(LocalDate.now());
	        course.setStatus("ACTIVE");
	        return courseRepository.save(course);
	    }
	    public Course updateCourse(
	            Long id,
	            Course course) {

	        Course existing = getCourse(id);

	        existing.setTitle(course.getTitle());
	        existing.setDescription(course.getDescription());
	        existing.setDuration(course.getDuration());
	        existing.setLevel(course.getLevel());
	        existing.setPrice(course.getPrice());

	        return courseRepository.save(existing);
	    }

	    public void deleteCourse(Long id) {
	        courseRepository.deleteById(id);
	    }
	    
	    public List<Course> searchCourse(String title) {
	        return courseRepository
	                .findByTitleContainingIgnoreCase(title);
	    }

	    public List<Course> getTeacherCourses(Long teacherId) {
	        return courseRepository
	                .findByTeacherTeacherId(teacherId);
	    }

}
