package com.coaching.service;

import org.springframework.stereotype.Service;
import com.coaching.dao.CourseDao;
import com.coaching.dao.EnrollmentDao;
import com.coaching.dao.StudentDao;
import com.coaching.dao.TeacherDao;
import com.coaching.dto.DashboardDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DashboardService {

    private final StudentDao studentDao;
    private final TeacherDao teacherDao;
    private final CourseDao courseDao;
    private final EnrollmentDao enrollDao;

    public DashboardDto getStats(){

        return new DashboardDto(studentDao.count(),teacherDao.count(),courseDao.count(),enrollDao.count()
        );
    }
}
