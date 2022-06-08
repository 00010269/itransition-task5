package com.itransition.task5.controller;

import com.itransition.task5.entity.User;
import com.itransition.task5.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/get-by-receiver-and-sender-id/{recipientId}")
    public ResponseEntity<?> getAllMessages(@PathVariable Integer recipientId, HttpSession session) {
        User user = (User)session.getAttribute("authUser");
        return messageService.getMessages(recipientId, user.getId());
    }
}
