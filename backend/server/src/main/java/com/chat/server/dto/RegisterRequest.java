package com.chat.server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Firstname must be required!")
    @Size(min = 3, max = 50, message = "Firstname must be between 3 and 50 characters long!")
    private String firstName;

    @NotBlank(message = "Lastname must be required!")
    @Size(min = 3, max = 50, message = "Lastname must be between 3 and 50 characters long!")
    private String lastName;

    @NotBlank(message = "Username must be required!")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters long!")
    private String username;

    @NotBlank(message = "Email is required?")
    @Email
    private String email;

    @NotBlank(message = "Password must be required!")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long!")
    private String password;
}
