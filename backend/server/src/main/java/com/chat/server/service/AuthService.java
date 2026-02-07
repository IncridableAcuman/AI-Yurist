package com.chat.server.service;

import com.chat.server.dto.*;
import com.chat.server.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final TokenService tokenService;
    private final ValidationService validationService;

    @Transactional
    public AuthResponse register(RegisterRequest request, HttpServletResponse response){
        User user = userService.create(request);
        return tokenService.issueToken(user,response);
    }
    public AuthResponse login(LoginRequest request,HttpServletResponse response){
        User user = userService.findByEmail(request.getEmail());
        validationService.validatePassword(request.getPassword(), user.getPassword());
        return tokenService.issueToken(user,response);
    }
    public AuthResponse refresh(String refreshToken,HttpServletResponse response){
        String email = tokenService.extractEmail(refreshToken);
        User user = userService.findByEmail(email);
        tokenService.validateToken(refreshToken, user.getId());
        return tokenService.issueToken(user,response);
    }
    @Transactional
    public void logout(String refreshToken,HttpServletResponse response){
        String email = tokenService.extractEmail(refreshToken);
        User user = userService.findByEmail(email);
        tokenService.validateToken(refreshToken, user.getId());
        tokenService.clearResponse(user.getId(), response);
    }
    public void forgotPassword(ForgotPasswordRequest request){
        User user = userService.findByEmail(request.getEmail());
        tokenService.resetToken(user);
    }
    public void resetPassword(ResetPasswordRequest request){
        String email = tokenService.extractEmail(request.getToken());
        User user = userService.findByEmail(email);
        tokenService.validateToken(request.getToken(),user.getId());
        userService.updatePassword(request.getPassword(),user);
    }
}
