package com.coaching.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.coaching.dao.AttendanceDao;
import com.coaching.dao.CourseDao;
import com.coaching.dao.StudentDao;
import com.coaching.dto.AttendanceDto;
import com.coaching.entity.Attendance;
import com.coaching.entity.Course;
import com.coaching.entity.Student;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {
	
	private final AttendanceDao attendanceDao;
    private final StudentDao studentDao;
    private final CourseDao courseDao;

    public Attendance markAttendance(AttendanceDto dto){

        Student student =
                studentDao.findById(dto.getStudentId())
                .orElseThrow();

        Course course =
                courseDao.findById(dto.getCourseId())
                .orElseThrow();

        Attendance attendance = new Attendance();

        attendance.setStudent(student);
        attendance.setCourse(course);
        attendance.setStatus(dto.getStatus());
        attendance.setAttendanceDate(LocalDate.now());

        return attendanceDao.save(attendance);
    }

    public List<Attendance> getStudentAttendance(Long studentId){

        return attendanceDao.findByStudentStudentId(studentId);
    }
}
