package com.coaching.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

import com.coaching.entity.ApiResponse;
import com.coaching.entity.StudyMaterial;
import com.coaching.service.StudyMaterialService;

import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StudyMaterialController {
	
	private final StudyMaterialService materialService;

	@PostMapping("/course/{courseId}")
	public ResponseEntity<ApiResponse<StudyMaterial>> uploadMaterial(
	        @PathVariable Long courseId,
	        @RequestBody StudyMaterial material) {

	    StudyMaterial saved = materialService.uploadMaterial(courseId, material);

	    return ResponseEntity.ok(new ApiResponse<>(
	                    true,
	                    "Material Uploaded",
	                    saved)
	    );
	}
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException{
    	 String path = "uploads/" + file.getOriginalFilename();
    	    file.transferTo(new File(path));

    	    return path;
    }
    
    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<StudyMaterial>>> getMaterials(
            @PathVariable Long courseId) {

        List<StudyMaterial> materials =
                materialService.getCourseMaterials(courseId);

        return ResponseEntity.ok(
                new ApiResponse<>(true,
                        "Study Material List",
                        materials));
    }

    @DeleteMapping("/{id}")
    public String deleteMaterial(
            @PathVariable Long id) {

        materialService.deleteMaterial(id);

        return "Material Deleted";
    }

}
