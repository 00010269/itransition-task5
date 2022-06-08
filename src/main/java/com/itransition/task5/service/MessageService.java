package com.itransition.task5.service;

import com.itransition.task5.dto.MessageDto;
import com.itransition.task5.entity.Message;
import com.itransition.task5.entity.User;
import com.itransition.task5.repository.MessageRepository;
import com.itransition.task5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    ChatRoomService chatRoomService;

    public ResponseEntity<?> getMessages(Integer recipientId, Integer senderId) {

        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new ResourceAccessException("User not found"));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceAccessException("User not found"));

        Integer chatId =chatRoomService.getChatId(recipient, sender, false);

        List<Message> messageList = messageRepository.findAllByChatId(chatId);
        List<MessageDto> messageDtoList = messageList.stream().map(message -> new MessageDto(
                message.getId(),
                message.getTitle(),
                message.getBody(),
                message.getRecipient().getId(),
                message.getRecipient().getUsername(),
                message.getSender().getId(),
                message.getSender().getUsername()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(messageDtoList);
    }


    public void send(MessageDto messageDto) {

        User recipient = userRepository.findById(messageDto.getRecipientId())
                .orElseThrow(() -> new ResourceAccessException("User not found"));

        User sender = userRepository.findById(messageDto.getSenderId())
                .orElseThrow(() -> new ResourceAccessException("User not found"));

        Message msg = messageRepository.save(new Message(
                sender,
                recipient,
                chatRoomService.getChatId(recipient, sender, true),
                messageDto.getTitle(),
                messageDto.getBody()
        ));

        messageDto.setId(msg.getId());

        messagingTemplate.convertAndSendToUser(
                messageDto.getRecipientId().toString(),
                "/queue/messages",
                messageDto
        );

    }


}
