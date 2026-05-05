package com.pooja.zoomer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.pooja.zoomer.entity.User;
import com.pooja.zoomer.entity.enums.UserRole;
import com.pooja.zoomer.entity.enums.UserStatus;
import com.pooja.zoomer.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User register(String name, String email, String phone, UserRole role) {

        User user = User.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .role(role)
               // .isActive(true)
                .build();

        return userRepository.save(user);
    }

    public User login(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔴 ADD THIS CHECK HERE
        if (user.getStatus() != UserStatus.APPROVED) {
            throw new RuntimeException("User not approved");
        }

        return user;
    }
}