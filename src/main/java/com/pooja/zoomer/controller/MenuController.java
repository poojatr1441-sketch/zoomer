package com.pooja.zoomer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.pooja.zoomer.dto.MenuItemResponseDTO;
import com.pooja.zoomer.entity.MenuItem;
import com.pooja.zoomer.service.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // 🔹 ADD MENU ITEM
    @PostMapping
    public MenuItem addMenuItem(@RequestParam Long restaurantId,
                               @RequestBody MenuItem item,
                               @RequestParam(required = false) List<Long> addonIds) {
        return menuService.addMenuItem(restaurantId, item, addonIds);
    }

    // 🔹 GET MENU
    @GetMapping("/{restaurantId}")
    public List<MenuItemResponseDTO> getMenu(@PathVariable Long restaurantId) {
        return menuService.getMenu(restaurantId);
    }
}