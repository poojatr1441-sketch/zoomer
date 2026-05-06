package com.pooja.zoomer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.pooja.zoomer.entity.User;
import com.pooja.zoomer.entity.enums.UserRole;
import com.pooja.zoomer.security.JwtUtil;
import com.pooja.zoomer.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String phone,
                         @RequestParam UserRole role) {

        return authService.register(name, email, phone, role);
    }

    @PostMapping("/login")
    public String login(@RequestParam String email) {

        User user = authService.login(email);

        return jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );
    }
}