package com.coaching.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.coaching.dao.CourseDao;
import com.coaching.dao.LectureDao;
import com.coaching.dao.TeacherDao;
import com.coaching.dao.UserDao;
import com.coaching.dto.CourseRequest;
import com.coaching.dto.LectureRequest;
import com.coaching.dto.TeacherRequest;
import com.coaching.entity.Course;
import com.coaching.entity.Lecture;
import com.coaching.entity.Teacher;
import com.coaching.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherService {
	
	private final TeacherDao teacherDao;
	private final UserDao userDao;
	private final CourseDao courseDao;
	private final LectureDao lectureDao;
	private final PasswordEncoder passwordEncoder;
	
	
	public Teacher createTeacher(TeacherRequest request){

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("TEACHER");

        user = userDao.save(user);

        Teacher teacher = new Teacher();

        teacher.setUser(user);
        teacher.setExpertise(request.getExpertise());
        teacher.setQualification(request.getQualification());
        teacher.setPhone(request.getPhone());
        teacher.setJoinDate(request.getJoinDate());

        return teacherDao.save(teacher);
    }

    public List<Teacher> getAllTeachers(){
        return teacherDao.findAll();
    }
    
    public List<Teacher> searchTeacher(String expertise) {
        return teacherDao
                .findByExpertiseContainingIgnoreCase(expertise);
    }

    public Teacher getTeacherById(Long id){

        return teacherDao.findById(id)
                .orElseThrow(() ->
                new RuntimeException("Teacher Not Found"));
    }
    
    public void createCourse(CourseRequest request) {

        Teacher teacher = teacherDao.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Course course = new Course();

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setDuration(request.getDuration());
        course.setPrice(request.getPrice());
        course.setLevel(request.getLevel());
        course.setCreatedDate(LocalDate.now());
        course.setStatus("ACTIVE");
        course.setTeacher(teacher);

        courseDao.save(course);
    }
    
    public void uploadLecture(LectureRequest request){

        Course course = courseDao.findById(request.getCourseId())
                .orElseThrow(() ->
                        new RuntimeException("Course Not Found"));

        Lecture lecture = new Lecture();

        lecture.setTitle(request.getTitle());

        lecture.setDescription(request.getDescription());

        lecture.setVideoUrl(request.getVideoUrl());

        lecture.setLectureOrder(request.getLectureOrder());

        lecture.setUploadDate(LocalDate.now());

        lecture.setCourse(course);

        lectureDao.save(lecture);

    }
    
    public List<Course> getTeacherCourses(Long teacherId) {

        return courseDao.findByTeacherTeacherId(teacherId);

    }


}
