package com.itransition.task5.service;

import com.itransition.task5.entity.ChatRoom;
import com.itransition.task5.entity.User;
import com.itransition.task5.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    public Integer getChatId(User recipient, User sender, boolean shouldCreateChatRoom) {

        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByRecipientAndSender(recipient, sender);

        if (optionalChatRoom.isPresent())
            return optionalChatRoom.get().getChatId();
        else if (!shouldCreateChatRoom)
            return null;

        Integer generatedChatId = (int) (Math.random() * 100_000);

        if (sender.getId().equals(recipient.getId())){
            ChatRoom chatRoom = new ChatRoom(
                    generatedChatId,
                    recipient,
                    sender
            );

            ChatRoom save = chatRoomRepository.save(chatRoom);
            return save.getChatId();
        }

        ArrayList<ChatRoom> chatRooms = new ArrayList<>();

        ChatRoom chatRoom = new ChatRoom(generatedChatId, recipient, sender);
        ChatRoom chatRoom2 = new ChatRoom(generatedChatId, sender, recipient);

        chatRooms.add(chatRoom);

        if (!sender.equals(recipient)) {
            chatRooms.add(chatRoom2);
        }

        List<ChatRoom> savedChatRooms = chatRoomRepository.saveAll(chatRooms);

        if (savedChatRooms.get(1).getChatId().equals(savedChatRooms.get(0).getChatId()))
            return savedChatRooms.get(0).getChatId();
        else return null;


    }

}
