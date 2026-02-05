package com.chat.server.dto;

import com.chat.server.entity.User;
import com.chat.server.enums.Role;

public record AuthResponse(
        String id,
        String email,
        Role role,
        String accessToken
) {
    public static AuthResponse from(User user,String accessToken){
        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                accessToken
                );
    }
}
