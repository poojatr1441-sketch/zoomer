package com.pooja.zoomer.service;

import org.springframework.stereotype.Service;

import com.pooja.zoomer.entity.User;
import com.pooja.zoomer.entity.enums.UserStatus;
import com.pooja.zoomer.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public void approveUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(UserStatus.APPROVED);
        userRepository.save(user);
    }

    public void suspendUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(UserStatus.SUSPENDED);
        userRepository.save(user);
    }
}