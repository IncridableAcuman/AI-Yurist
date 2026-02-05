package com.chat.server.service;

import com.chat.server.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String,Object> template;

    @Value("${jwt.refresh_time}")
    private int refreshTime;

    public String getKey(String userId){
        return "refreshToken:"+userId;
    }
    public void saveToken(String userId,String refreshToken){
        if (userId == null || userId.isEmpty()){
            throw new BadRequestException("User ID is null or empty!");
        }
        if (refreshToken == null || refreshToken.isEmpty()){
            throw new BadRequestException("Refresh Token is null or empty!");
        }
        String key = getKey(userId);
        template.opsForValue()
                .set(
                        key,
                        refreshToken,
                        refreshTime,
                        TimeUnit.MILLISECONDS
                );
    }

    public String getTokenFromCache(String userId){
        if (userId == null) return null;
        Object token = template.opsForValue().get(getKey(userId));
        return token != null ? token.toString() : null;
    }

    public void deleteTokenFromCache(String userId){
        if (userId == null) return;
        template.delete(getKey(userId));
    }
}
