package com.coaching.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coaching.entity.Message;
import com.coaching.service.MessageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService service;

    @PostMapping
    public Message sendMessage(@RequestBody Message message){

        return service.sendMessage(message);
    }

    @GetMapping("/inbox/{userId}")
    public List<Message> inbox(@PathVariable Long userId){

        return service.inbox(userId);
    }
}
