package com.chat.server.dto;

public record TokenPair(
        String accessToken,
        String refreshToken
) {
}
