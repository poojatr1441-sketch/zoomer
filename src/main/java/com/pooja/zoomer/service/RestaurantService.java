package com.pooja.zoomer.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.pooja.zoomer.entity.*;
import com.pooja.zoomer.repository.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    //create or registering new restaurant
    public Restaurant createRestaurant(Long ownerId, Restaurant restaurant) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        restaurant.setOwner(owner);
        restaurant.setIsOpen(true); // default open
        restaurant.setRating(BigDecimal.ZERO);
        
        return restaurantRepository.save(restaurant);
    }
    
    //open/close toggle
    @Transactional
    public Restaurant toggleRestaurant(Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurant.setIsOpen(!restaurant.getIsOpen());

        return restaurantRepository.save(restaurant);
    }
}