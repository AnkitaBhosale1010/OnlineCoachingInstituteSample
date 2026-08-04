package com.coaching.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coaching.entity.Message;

public interface MessageDao extends JpaRepository<Message, Long> {

	List<Message> findByReceiverUserIdOrderBySentAtDesc(Long userId);

    List<Message> findBySenderUserIdAndReceiverUserId(
            Long senderUserId,
            Long receiverUserId);
    
    @Query("""
            SELECT m
            FROM Message m
            WHERE
            (m.sender.userId = ?1 AND m.receiver.userId = ?2)
            OR
            (m.sender.userId = ?2 AND m.receiver.userId = ?1)
            ORDER BY m.sentAt ASC
            """)
    
    List<Message> getConversation(Long user1, Long user2);

}

