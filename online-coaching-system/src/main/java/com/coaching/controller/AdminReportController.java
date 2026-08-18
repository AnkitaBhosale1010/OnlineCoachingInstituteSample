package com.coaching.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coaching.dto.AdminReportResponseDto;
import com.coaching.service.AdminReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminReportController {

    private final AdminReportService adminReportService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<AdminReportResponseDto> getAdminReport() {

        return ResponseEntity.ok(
                adminReportService.getAdminReport()
        );
    }
}