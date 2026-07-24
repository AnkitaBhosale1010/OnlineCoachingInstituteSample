package com.coaching.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coaching.dto.MessageRequest;
import com.coaching.entity.ApiResponse;
import com.coaching.entity.Message;
import com.coaching.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService service;

    @PostMapping
    public ResponseEntity<ApiResponse<Message>> sendMessage(@Valid @RequestBody MessageRequest request){

    	Message message = service.sendMessage(request);

        return ResponseEntity.status(HttpStatus.CREATED)

                .body(new ApiResponse<>(
                                true,
                                "Message Sent Successfully",
                                message));
    }

    @GetMapping("/inbox/{userId}")
    public List<Message> inbox(@PathVariable Long userId){

        return service.inbox(userId);
    }
}
