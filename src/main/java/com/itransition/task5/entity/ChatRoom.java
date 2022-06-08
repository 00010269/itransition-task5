package com.itransition.task5.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer chatId;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;


    public ChatRoom(Integer chatId, User sender, User recipient) {
        this.chatId = chatId;
        this.sender = sender;
        this.recipient = recipient;
    }


}
