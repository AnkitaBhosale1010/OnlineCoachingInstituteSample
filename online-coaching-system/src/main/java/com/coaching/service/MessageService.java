package com.coaching.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.coaching.dao.MessageDao;
import com.coaching.entity.Message;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageDao Dao;

    public Message sendMessage(Message message){

        message.setSentAt(LocalDateTime.now());

        return Dao.save(message);
    }

    public List<Message> inbox(Long userId){

        return Dao.findByReceiverUserId(userId);
    }
}
