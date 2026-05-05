package com.pooja.zoomer.controller;

import org.springframework.web.bind.annotation.*;

import com.pooja.zoomer.service.CartService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public String addToCart(@RequestParam Long userId,
                           @RequestParam Long menuItemId,
                           @RequestParam int quantity,
                           @RequestParam(required = false) List<Long> addonIds) {

        cartService.addToCart(userId, menuItemId, quantity, addonIds);
        return "Item added to cart";
    }
}