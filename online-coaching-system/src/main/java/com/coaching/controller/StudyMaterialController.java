package com.coaching.controller;

import java.io.File;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.coaching.entity.ApiResponse;
import com.coaching.entity.StudyMaterial;
import com.coaching.service.StudyMaterialService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StudyMaterialController {

    private final StudyMaterialService materialService;

    // Save Material using File URL (JSON Request)
    @PostMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<StudyMaterial>> saveMaterial(
            @PathVariable Long courseId,
            @RequestBody StudyMaterial material) {

        StudyMaterial saved =
                materialService.uploadMaterial(courseId, material);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Material Uploaded Successfully",
                        saved
                )
        );
    }

    // Upload PDF File
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<StudyMaterial>> uploadMaterial(
            @RequestParam("title") String title,
            @RequestParam("courseId") Long courseId,
            @RequestParam("file") MultipartFile file) {

        try {

            String uploadDir = "uploads/materials/";

            File folder = new File(uploadDir);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            String fileName =
                    System.currentTimeMillis() + "_"
                            + file.getOriginalFilename();

            File destination =
                    new File(uploadDir + fileName);

            file.transferTo(destination);

            StudyMaterial material =
                    materialService.saveUploadedMaterial(
                            courseId,
                            title,
                            fileName
                    );

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Material Uploaded Successfully",
                            material
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(
                            new ApiResponse<>(
                                    false,
                                    e.getMessage(),
                                    null
                            )
                    );
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<StudyMaterial>>> getMaterials(
            @PathVariable Long courseId) {

        List<StudyMaterial> materials =
                materialService.getCourseMaterials(courseId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Study Material List",
                        materials
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMaterial(
            @PathVariable Long id) {

        materialService.deleteMaterial(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Material Deleted Successfully",
                        null
                )
        );
    }

}