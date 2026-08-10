package com.coaching.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coaching.dao.CourseDao;
import com.coaching.dao.StudyMaterialDao;
import com.coaching.entity.Course;
import com.coaching.entity.StudyMaterial;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyMaterialService {

	private final StudyMaterialDao materialDao;
    private final CourseDao courseDao;

    public StudyMaterial uploadMaterial(Long courseId,StudyMaterial material) {

        Course course =courseDao.findById(courseId)
                        .orElseThrow(() ->new RuntimeException("Course Not Found"));

        material.setCourse(course);
        material.setUploadDate(LocalDate.now());

        return materialDao.save(material);
    }

    public StudyMaterial saveUploadedMaterial(
            Long courseId,
            String title,
            String fileName) {

        Course course =
                courseDao.findById(courseId)
                        .orElseThrow(() ->
                                new RuntimeException("Course Not Found"));

        StudyMaterial material = new StudyMaterial();

        material.setTitle(title);

        material.setFileUrl(
                "http://192.168.113.84:8080/uploads/materials/" + fileName
        );
        
        material.setUploadDate(LocalDate.now());

        material.setCourse(course);

        return materialDao.save(material);

    }
     
    public List<StudyMaterial> getCourseMaterials(
            Long courseId) {

        return materialDao.findByCourseCourseId(courseId);
    }

    public void deleteMaterial(Long id) {
    	materialDao.deleteById(id);
    }
}
