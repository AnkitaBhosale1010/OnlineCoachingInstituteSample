package com.coaching.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class StudentRequest {

    private String name;
    private String email;
    private String password;

    private String address;
    private LocalDate dob;
    private String phone;
    private LocalDate joinDate;
}
