package com.itransition.task5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDto {

    private Integer id;
    private String title;
    private String body;
    private Integer recipientId;
    private String recipientName;
    private Integer senderId;
    private String senderName;

}
