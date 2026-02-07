package com.chat.server.service;

import com.chat.server.dto.MessageRequest;
import com.chat.server.dto.MessageResponse;
import com.chat.server.entity.Message;
import com.chat.server.entity.User;
import com.chat.server.enums.SenderType;
import com.chat.server.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final AIService aiService;

    public MessageResponse sendMessage(MessageRequest request, User user){
        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(user.getId());
        message.setType(SenderType.USER);
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);

        String response = aiService.getLegalAdvice(request.getContent(), user);

        Message aiMessage = new Message();
        aiMessage.setSender("chat");
        aiMessage.setType(SenderType.AI);
        aiMessage.setContent(response);
        aiMessage.setTimestamp(LocalDateTime.now());
        messageRepository.save(aiMessage);
        return MessageResponse.from(aiMessage);
    }
    public List<MessageResponse> getList(User user){
        List<Message> messages = messageRepository.findBySender(user.getId());
        return messages.stream()
                .map(MessageResponse::from).toList();
    }
}
