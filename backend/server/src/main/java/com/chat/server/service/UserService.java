package com.chat.server.service;

import com.chat.server.dto.RegisterRequest;
import com.chat.server.entity.User;
import com.chat.server.enums.Role;
import com.chat.server.exception.BadRequestException;
import com.chat.server.exception.NotFoundException;
import com.chat.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new NotFoundException("User not found!"));
    }

    @Transactional
    public User create(RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new BadRequestException("User already exist!");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        return saveUser(user);
    }

    @Transactional
    public void updatePassword(String password,User user){
        user.setPassword(passwordEncoder.encode(password));
        saveUser(user);
    }

}
