package com.chat.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "Token is required!")
    private String token;

    @NotBlank(message = "Password must be required!")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long!")
    private String password;
}
