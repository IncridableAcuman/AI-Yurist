package com.chat.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageRequest {
    @NotBlank(message = "Content is required!")
    @Size(min = 1,message = "Content should be greater than 1 characters long!")
    private String content;
}
