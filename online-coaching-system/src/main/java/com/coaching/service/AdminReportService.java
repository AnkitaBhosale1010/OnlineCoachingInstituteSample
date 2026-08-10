package com.coaching.service;

import org.springframework.stereotype.Service;

import com.coaching.dao.BatchDao;
import com.coaching.dao.CourseDao;
import com.coaching.dao.EnrollmentDao;
import com.coaching.dao.QuizResultDao;
import com.coaching.dao.StudentDao;
import com.coaching.dao.SubmissionDao;
import com.coaching.dao.TeacherDao;
import com.coaching.dto.AdminReportResponseDto;

@Service
public class AdminReportService {

    private final StudentDao studentDao;
    private final TeacherDao teacherDao;
    private final CourseDao courseDao;
    private final BatchDao batchDao;
    private final EnrollmentDao enrollmentDao;
    private final QuizResultDao quizResultDao;
    private final SubmissionDao submissionDao;

    public AdminReportService(
            StudentDao studentDao,
            TeacherDao teacherDao,
            CourseDao courseDao,
            BatchDao batchDao,
            EnrollmentDao enrollmentDao,
            QuizResultDao quizResultDao,
            SubmissionDao submissionDao) {

        this.studentDao = studentDao;
        this.teacherDao = teacherDao;
        this.courseDao = courseDao;
        this.batchDao = batchDao;
        this.enrollmentDao = enrollmentDao;
        this.quizResultDao = quizResultDao;
        this.submissionDao = submissionDao;
    }

    public AdminReportResponseDto getAdminReport() {

        AdminReportResponseDto report =
                new AdminReportResponseDto();

        report.setTotalStudents(studentDao.count());
        report.setTotalTeachers(teacherDao.count());
        report.setTotalCourses(courseDao.count());
        report.setTotalBatches(batchDao.count());
        report.setTotalEnrollments(enrollmentDao.count());
        report.setTotalQuizResults(quizResultDao.count());
        report.setTotalSubmissions(submissionDao.count());

        return report;
    }
}