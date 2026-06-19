package com.coaching.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TeacherRequest {

    private String name;
    private String email;
    private String password;

    private String expertise;
    private String qualification;
    private String phone;
    private LocalDate joinDate;
}
