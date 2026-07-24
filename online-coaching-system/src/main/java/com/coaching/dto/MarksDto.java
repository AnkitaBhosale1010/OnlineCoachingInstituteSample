package com.coaching.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MarksDto {

	@NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer marks;
    private String feedback;
}
