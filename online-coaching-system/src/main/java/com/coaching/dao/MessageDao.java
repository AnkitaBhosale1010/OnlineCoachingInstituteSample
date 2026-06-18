package com.coaching.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.Message;

public interface MessageDao extends JpaRepository<Message, Long> {

	List<Message> findByReceiverUserIdOrderBySentAtDesc(Long userId);

    List<Message> findBySenderUserIdAndReceiverUserId(
            Long senderUserId,
            Long receiverUserId);
}
