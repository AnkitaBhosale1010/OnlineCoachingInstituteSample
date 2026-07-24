package com.coaching.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequest {

	@NotNull
    private Long senderId;
	
	@NotNull
    private Long receiverId;
	
	@NotBlank(message = "Message cannot be empty")
    private String message;
    private LocalDateTime sentAt;
}
