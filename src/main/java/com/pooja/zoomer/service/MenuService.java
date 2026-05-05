package com.pooja.zoomer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pooja.zoomer.entity.MenuItem;
import com.pooja.zoomer.entity.Restaurant;
import com.pooja.zoomer.repository.MenuItemRepository;
import com.pooja.zoomer.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    // 🔹 ADD MENU ITEM
    public MenuItem addMenuItem(Long restaurantId, MenuItem item) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        item.setRestaurant(restaurant);
        item.setIsAvailable(true); // important

        return menuItemRepository.save(item);
    }

    // 🔹 GET MENU BY RESTAURANT
    public List<MenuItem> getMenu(Long restaurantId) {
        return menuItemRepository.findByRestaurant_RestaurantId(restaurantId);
    }
}