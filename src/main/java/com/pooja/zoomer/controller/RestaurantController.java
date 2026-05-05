package com.pooja.zoomer.controller;

import org.springframework.web.bind.annotation.*;

import com.pooja.zoomer.entity.Restaurant;
import com.pooja.zoomer.service.RestaurantService;

import lombok.*;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public Restaurant createRestaurant(@RequestParam Long ownerId,
                                       @RequestBody Restaurant restaurant) {
        return restaurantService.createRestaurant(ownerId, restaurant);
    }
    
    @PatchMapping("/{restaurantId}/toggle")
    public Restaurant toggleRestaurant(@PathVariable Long restaurantId) {
        return restaurantService.toggleRestaurant(restaurantId);
    }
}