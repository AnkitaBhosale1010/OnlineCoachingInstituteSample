package com.coaching.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coaching.dto.DashboardDto;
import com.coaching.entity.User;
import com.coaching.service.AdminService;
import com.coaching.service.DashboardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

    private final DashboardService dashboardService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public DashboardDto dashboard(){

        return dashboardService.getStats();
    }
    
    @GetMapping("/users")
    public List<User> getUsers(){

        return adminService.getAllUsers();
    }
}
