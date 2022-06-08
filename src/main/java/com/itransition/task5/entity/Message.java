package com.itransition.task5.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    private Integer chatId;

    private String title;

    private String body;


    public Message(User sender, User recipient, Integer chatId, String title, String body) {
        this.sender = sender;
        this.recipient = recipient;
        this.chatId = chatId;
        this.title = title;
        this.body = body;
    }
}
