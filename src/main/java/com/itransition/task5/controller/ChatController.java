package com.itransition.task5.controller;

import com.itransition.task5.dto.MessageDto;
import com.itransition.task5.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {

    @Autowired
    MessageService messageService;

    @MessageMapping("/send-message")
    public void sendMessage(MessageDto messageDto) throws Exception {
        messageService.send(messageDto);
    }








}
