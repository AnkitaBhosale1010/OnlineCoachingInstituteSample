package com.coaching.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.coaching.dto.AttendanceDto;
import com.coaching.entity.ApiResponse;
import com.coaching.entity.Attendance;
import com.coaching.service.AttendanceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
	
	 private final AttendanceService attendanceService;

	    @PostMapping
	    public ResponseEntity<ApiResponse<Attendance>> markAttendance(
	            @RequestBody AttendanceDto dto){

	        Attendance attendance =
	                attendanceService.markAttendance(dto);

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Attendance Marked Successfully",
	                        attendance));
	    }

	    @GetMapping("/student/{studentId}")
	    public ResponseEntity<ApiResponse<List<Attendance>>> getAttendance(
	            @PathVariable Long studentId){

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Attendance List",
	                        attendanceService.getStudentAttendance(studentId)));
	    }
}
