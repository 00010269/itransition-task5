package com.itransition.task5.repository;

import com.itransition.task5.entity.ChatRoom;
import com.itransition.task5.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {


    Optional<ChatRoom> findBySenderIdAndRecipientId(Integer sender_id, Integer recipient_id);


    Optional<ChatRoom> findByRecipientAndSender(User recipient, User sender);
}
