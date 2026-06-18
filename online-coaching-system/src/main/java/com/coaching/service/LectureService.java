package com.coaching.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.coaching.dao.CourseDao;
import com.coaching.dao.LectureDao;
import com.coaching.entity.Course;
import com.coaching.entity.Lecture;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureService {
	
	private final LectureDao lectureDao;
    private final CourseDao courseDao;

    public Lecture addLecture(Long courseId,Lecture lecture) {

        Course course =courseDao.findById(courseId)
                        .orElseThrow(() ->new RuntimeException("Course Not Found"));

        lecture.setCourse(course);
        lecture.setUploadDate(LocalDate.now());

        return lectureDao.save(lecture);
    }

    public List<Lecture> getCourseLectures(
            Long courseId) {

        return lectureDao.findByCourseCourseId(courseId);
    }

    public Lecture getLecture(Long id) {

        return lectureDao.findById(id)
                .orElseThrow(() ->new RuntimeException("Lecture Not Found"));
    }

    public Lecture updateLecture(Long id,Lecture lecture) {

        Lecture existing = getLecture(id);

        existing.setTitle(lecture.getTitle());
        existing.setDescription(lecture.getDescription());
        existing.setVideoUrl(lecture.getVideoUrl());
        existing.setLectureOrder(lecture.getLectureOrder());

        return lectureDao.save(existing);
    }

    public void deleteLecture(Long id) {
        lectureDao.deleteById(id);
    }
}
