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

import com.coaching.entity.Lecture;
import com.coaching.service.LectureService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LectureController {

	private final LectureService lectureService;

    @PostMapping("/course/{courseId}")
    public Lecture addLecture(@PathVariable Long courseId,@RequestBody Lecture lecture) {

        return lectureService.addLecture(courseId, lecture);
    }

    @GetMapping("/course/{courseId}")
    public List<Lecture> getCourseLectures(@PathVariable Long courseId) {

        return lectureService.getCourseLectures(courseId);
    }

    @GetMapping("/{id}")
    public Lecture getLecture(@PathVariable Long id) {

        return lectureService.getLecture(id);
    }

    @PutMapping("/{id}")
    public Lecture updateLecture(@PathVariable Long id,@RequestBody Lecture lecture) {

        return lectureService.updateLecture(id, lecture);
    }

    @DeleteMapping("/{id}")
    public String deleteLecture(@PathVariable Long id) {

        lectureService.deleteLecture(id);

        return "Lecture Deleted";
    }
}
