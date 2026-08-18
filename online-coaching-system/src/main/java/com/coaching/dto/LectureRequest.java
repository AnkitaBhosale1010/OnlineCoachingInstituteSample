package com.coaching.dto;

import lombok.Data;

@Data
public class LectureRequest {

    private Long courseId;

    private String title;

    private String description;

    private String videoUrl;

    private Integer lectureOrder;

}