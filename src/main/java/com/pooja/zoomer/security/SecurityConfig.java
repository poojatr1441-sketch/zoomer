package com.pooja.zoomer.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                // ❌ Disable CSRF (not needed for REST)
                .csrf(csrf -> csrf.disable())

                // ❌ Disable default login
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // ✅ Stateless session (JWT)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 🔐 Role-based access control
                .authorizeHttpRequests(auth -> auth

                        // 🔓 PUBLIC
                        .requestMatchers("/auth/**").permitAll()

                        // 👤 CUSTOMER APIs
                        .requestMatchers("/cart/**", "/order/**").hasRole("CUSTOMER")

                        // 🍴 OWNER APIs
                        .requestMatchers("/restaurant/**", "/menu/**").hasRole("OWNER")

                        // 🚚 DELIVERY APIs
                        .requestMatchers("/delivery/**").hasRole("DELIVERY")

                        // 🛠 ADMIN APIs
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 🔐 everything else
                        .anyRequest().authenticated()
                )

                // ✅ JWT filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}