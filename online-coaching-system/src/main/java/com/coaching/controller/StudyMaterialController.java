package com.coaching.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coaching.entity.StudyMaterial;
import com.coaching.service.StudyMaterialService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StudyMaterialController {
	
	private final StudyMaterialService materialService;

    @PostMapping("/course/{courseId}")
    public StudyMaterial uploadMaterial(@PathVariable Long courseId,@RequestBody StudyMaterial material) {

        return materialService.uploadMaterial(courseId, material);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return file.getOriginalFilename();
    }
    
    @GetMapping("/course/{courseId}")
    public List<StudyMaterial> getMaterials(
            @PathVariable Long courseId) {

        return materialService
                .getCourseMaterials(courseId);
    }

    @DeleteMapping("/{id}")
    public String deleteMaterial(
            @PathVariable Long id) {

        materialService.deleteMaterial(id);

        return "Material Deleted";
    }

}
