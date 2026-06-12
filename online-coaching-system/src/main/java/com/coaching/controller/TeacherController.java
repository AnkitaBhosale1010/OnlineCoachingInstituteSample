package com.coaching.controller;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coaching.entity.Teacher;
import com.coaching.service.TeacherService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TeacherController {
	
	 private final TeacherService teacherService;

	    @GetMapping
	    public List<Teacher> getAllTeachers() {
	        return teacherService.getAllTeachers();
	    }

	    @GetMapping("/{id}")
	    public Teacher getTeacher(@PathVariable Long id) {
	        return teacherService.getTeacherById(id);
	    }

	    @PostMapping
	    public Teacher addTeacher(@RequestBody Teacher teacher) {
	        return teacherService.saveTeacher(teacher);
	    }

	    @PutMapping("/{id}")
	    public Teacher updateTeacher(
	            @PathVariable Long id,
	            @RequestBody Teacher teacher) {

	        return teacherService.updateTeacher(id, teacher);
	    }

	    @DeleteMapping("/{id}")
	    public String deleteTeacher(@PathVariable Long id) {

	        teacherService.deleteTeacher(id);

	        return "Teacher Deleted Successfully";
	    }
}
