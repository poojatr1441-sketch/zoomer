package com.pooja.zoomer.service;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .status(UserStatus.PENDING)
                .build();

        return userRepository.save(user);
    }

    public User login(String email) {

    	System.out.println("===== LOGIN METHOD ENTERED =====");
    	
    	User user = userRepository.findByEmail(email)
    	        .orElseThrow(() ->
    	                new ResponseStatusException(
    	                        HttpStatus.NOT_FOUND,
    	                        "User not found"
    	                ));
    	
    	System.out.println("EMAIL = " + user.getEmail());
        System.out.println("ROLE = " + user.getRole());
        System.out.println("STATUS = " + user.getStatus());
    	
        // 🔴 ADD THIS CHECK HERE
    	if (user.getStatus() != UserStatus.APPROVED) {
    	    throw new ResponseStatusException(
    	            HttpStatus.FORBIDDEN,
    	            "User not approved"
    	    );
    	}

        return user;
    }
}