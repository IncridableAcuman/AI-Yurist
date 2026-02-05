package com.chat.server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email is required?")
    @Email
    private String email;

    @NotBlank(message = "Password must be required!")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long!")
    private String password;
}

