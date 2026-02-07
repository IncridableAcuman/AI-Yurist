package com.chat.server.service;

import com.chat.server.dto.AuthResponse;
import com.chat.server.dto.TokenPair;
import com.chat.server.entity.User;
import com.chat.server.exception.UnAuthorizedException;
import com.chat.server.util.CookieUtil;
import com.chat.server.util.JwtUtil;
import com.chat.server.util.MailUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final RedisService redisService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final MailUtil mailUtil;

    public AuthResponse issueToken(User user, HttpServletResponse response){
        TokenPair tokens = jwtUtil.getTokens(user);
        redisService.saveToken(user.getId(), tokens.refreshToken());
        cookieUtil.addCookie(tokens.refreshToken(),response);
        return AuthResponse.from(user, tokens.accessToken());
    }
    public String extractEmail(String token){
        return jwtUtil.extractSubject(token);
    }
    public void clearResponse(String userId,HttpServletResponse response){
        redisService.deleteTokenFromCache(userId);
        cookieUtil.clearCookie(response);
    }
    public void validateToken(String token,String userId){
        String cacheToken = redisService.getTokenFromCache(userId);
        if (!jwtUtil.validateToken(token) || !cacheToken.equals(token)){
            throw new UnAuthorizedException("Token is invalid or expired!");
        }
    }
    public void resetToken(User user){
        TokenPair tokens = jwtUtil.getTokens(user);
        String url = "http://localhost:5173/reset-password?token="+tokens.accessToken();
        mailUtil.sendMail(user.getEmail(),"Reset Password",url);
    }
}
