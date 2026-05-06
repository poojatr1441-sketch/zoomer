package com.pooja.zoomer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pooja.zoomer.dto.AddonDTO;
import com.pooja.zoomer.dto.MenuItemResponseDTO;
import com.pooja.zoomer.entity.Addon;
import com.pooja.zoomer.entity.MenuItem;
import com.pooja.zoomer.entity.MenuItemAddon;
import com.pooja.zoomer.entity.MenuItemAddonId;
import com.pooja.zoomer.entity.Restaurant;
import com.pooja.zoomer.repository.AddonRepository;
import com.pooja.zoomer.repository.MenuItemAddonRepository;
import com.pooja.zoomer.repository.MenuItemRepository;
import com.pooja.zoomer.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
 
    private final AddonRepository addonRepository;
    private final MenuItemAddonRepository menuItemAddonRepository;
    // 🔹 ADD MENU ITEM
    public MenuItem addMenuItem(Long restaurantId, MenuItem item, List<Long> addonIds) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        item.setRestaurant(restaurant);

        MenuItem savedItem = menuItemRepository.save(item);

        if (addonIds != null && !addonIds.isEmpty()) {

            for (Long addonId : addonIds) {

                Addon addon = addonRepository.findById(addonId)
                        .orElseThrow(() -> new RuntimeException("Addon not found"));

                MenuItemAddon mapping = new MenuItemAddon();
                mapping.setId(new MenuItemAddonId(savedItem.getMenuItemId(), addonId));
                mapping.setMenuItem(savedItem);
                mapping.setAddon(addon);

                menuItemAddonRepository.save(mapping);
                
            }
        }

        return menuItemRepository.findById(savedItem.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("Menu not found"));
    }

    // 🔹 GET MENU BY RESTAURANT
    public List<MenuItemResponseDTO> getMenu(Long restaurantId) {

        return menuItemRepository.findByRestaurant_RestaurantId(restaurantId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    
    //menu entity to DTO
    public MenuItemResponseDTO convertToDTO(MenuItem item) {

        return MenuItemResponseDTO.builder()
                .menuItemId(item.getMenuItemId())
                .name(item.getName())
                .category(item.getCategory().name())
                .price(item.getPrice().doubleValue())
                .isAvailable(item.getIsAvailable())
                .addons(
                    item.getMenuItemAddons() == null ? List.of() :
                    item.getMenuItemAddons().stream()
                            .map(mia -> AddonDTO.builder()
                                    .addonId(mia.getAddon().getAddonId())
                                    .name(mia.getAddon().getName())
                                    .price(mia.getAddon().getPrice().doubleValue())
                                    .build()
                            ).toList()
                )
                .build();
    }
}