package com.chat.server.util;

import com.chat.server.dto.TokenPair;
import com.chat.server.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.refresh_time}")
    private int refreshTime;

    @Value("${jwt.access_time}")
    private int accessTime;

    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(User user, int expirationTime){
        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .claim("id",user.getId())
                .claim("role",user.getRole())
                .signWith(key)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .compact();
    }
    public TokenPair getTokens(User user){
        String accessToken = generateToken(user,accessTime);
        String refreshToken = generateToken(user,refreshTime);
        return new TokenPair(accessToken,refreshToken);
    }
    public Claims extractClaim(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractSubject(String token){
        return extractClaim(token).getSubject();
    }
    public Date extractExpiration(String token){
        return extractClaim(token).getExpiration();
    }
    public boolean validateToken(String token){
        try {
            return extractExpiration(token).after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
