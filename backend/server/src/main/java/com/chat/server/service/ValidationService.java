package com.chat.server.service;

import com.chat.server.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    public void validatePassword(String rawPassword,String encodePassword){
        if (!passwordEncoder.matches(rawPassword,encodePassword)){
            throw new BadRequestException("Password is incorrect!");
        }
    }
}
