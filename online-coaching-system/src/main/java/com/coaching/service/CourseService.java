package com.coaching.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.coaching.dao.CourseDao;
import com.coaching.dao.TeacherDao;
import com.coaching.entity.Course;
import com.coaching.entity.Teacher;
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
	                        new RuntimeException("Course Not Found"));
	    }

	    public Course createCourse(
	            Course course,
	            Long teacherId) {

	        Teacher teacher =
	        		teacherDao.findById(teacherId)
	                        .orElseThrow(() ->
	                                new RuntimeException("Teacher Not Found"));

	        course.setTeacher(teacher);

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
