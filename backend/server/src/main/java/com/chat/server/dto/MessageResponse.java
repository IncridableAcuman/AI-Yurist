package com.chat.server.dto;

import com.chat.server.entity.Message;
import com.chat.server.enums.SenderType;

import java.time.LocalDateTime;

public record MessageResponse(
        String id,
        String sender,
        SenderType type,
        String content,
        LocalDateTime timestamp
) {
    public static MessageResponse from(Message message){
        return new MessageResponse(
                message.getId(),
                message.getSender(),
                message.getType(),
                message.getContent(),
                message.getTimestamp()
        );
    }
}
