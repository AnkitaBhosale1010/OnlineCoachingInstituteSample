package com.coaching.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CourseRequest {

	    private Long teacherId;
	    private String title;
	    private String description;
	    private String duration;
	    private BigDecimal price;
	    private String level;
	      
}
 