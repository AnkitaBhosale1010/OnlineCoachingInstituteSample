package com.coaching.controller;

import java.io.File;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.coaching.entity.ApiResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam MultipartFile file) throws IOException {

        String path ="uploads/" +
                file.getOriginalFilename();

        file.transferTo(new File(path));

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "File Uploaded Successfully",
                        path));
    }
}
