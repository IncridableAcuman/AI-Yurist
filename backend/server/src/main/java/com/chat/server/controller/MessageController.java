package com.chat.server.controller;

import com.chat.server.dto.MessageRequest;
import com.chat.server.dto.MessageResponse;
import com.chat.server.entity.User;
import com.chat.server.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageRequest request, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(messageService.sendMessage(request,user));
    }
    @GetMapping
    public ResponseEntity<List<MessageResponse>> getList(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(messageService.getList(user));
    }
}
