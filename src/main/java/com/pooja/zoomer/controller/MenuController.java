package com.pooja.zoomer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

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
                               @RequestBody MenuItem item) {
        return menuService.addMenuItem(restaurantId, item);
    }

    // 🔹 GET MENU
    @GetMapping("/{restaurantId}")
    public List<MenuItem> getMenu(@PathVariable Long restaurantId) {
        return menuService.getMenu(restaurantId);
    }
}