package com.coaching.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.coaching.dto.QuizResultDto;
import com.coaching.entity.ApiResponse;
import com.coaching.entity.QuizResult;
import com.coaching.service.QuizResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class QuizResultController {
	
	 private final QuizResultService resultService;

	    @PostMapping
	    public ResponseEntity<ApiResponse<QuizResult>> saveResult(
	            @Valid @RequestBody QuizResultDto dto){

	        QuizResult result = resultService.saveResult(dto);

	        return ResponseEntity.ok(
	                new ApiResponse<>(
	                        true,
	                        "Quiz Result Saved Successfully",
	                        result));
	    }
}
