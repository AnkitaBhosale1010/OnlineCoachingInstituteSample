package com.coaching.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.StudyMaterial;

public interface StudyMaterialDao extends JpaRepository<StudyMaterial, Long> {

    List<StudyMaterial> findByCourseCourseId(
            Long courseId);
}
