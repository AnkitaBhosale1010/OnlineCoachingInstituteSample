package com.coaching.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coaching.dto.StudentRequest;
import com.coaching.entity.Student;
import com.coaching.service.StudentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
	
	private final StudentService studentService;

	@PostMapping
    public Student createStudent(@RequestBody StudentRequest request){

        return studentService.createStudent(request);
    }

    @GetMapping
    public List<Student> getAllStudents(){

        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id){

        return studentService.getStudentById(id);
    }
}
