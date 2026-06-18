package com.coaching.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDto {

    private long students;
    private long teachers;
    private long courses;
    private long enrollments;
}
