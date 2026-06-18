package com.coaching.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.coaching.dao.MessageDao;
import com.coaching.dao.UserDao;
import com.coaching.dto.MessageRequest;
import com.coaching.entity.Message;
import com.coaching.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageDao Dao;
    private final UserDao userDao;
   

    public List<Message> inbox(Long userId){

        return Dao.findByReceiverUserIdOrderBySentAtDesc(userId);
    }

	public Message sendMessage(MessageRequest request) {
		User sender = userDao.findById(request.getSenderId())
                .orElseThrow();

        User receiver = userDao.findById(request.getReceiverId())
                .orElseThrow();

        Message msg = new Message();

        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setMessage(request.getMessage());

        return Dao.save(msg);
	}
}
