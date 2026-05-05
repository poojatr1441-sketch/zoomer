package com.pooja.zoomer.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pooja.zoomer.entity.*;
import com.pooja.zoomer.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final AddonRepository addonRepository;
    private final CartItemAddonRepository cartItemAddonRepository;

    @Transactional
    public void addToCart(Long userId, Long menuItemId, int quantity, List<Long> addonIds) {

        // 🔹 VALIDATION
        if (quantity <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        // 🔹 GET MENU ITEM
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        // 🔹 CHECK RESTAURANT STATUS
        if (!menuItem.getRestaurant().getIsOpen()) {
            throw new RuntimeException("Restaurant is closed");
        }

        // 🔹 CHECK ITEM AVAILABILITY
        if (!menuItem.getIsAvailable()) {
            throw new RuntimeException("Item not available");
        }

        // 🔹 GET OR CREATE CART
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();

                    User user = new User();
                    user.setUserId(userId);

                    newCart.setUser(user);
                    newCart.setRestaurant(menuItem.getRestaurant());

                    return cartRepository.save(newCart);
                });

        // 🔥 FIX 1: SET RESTAURANT IF NULL and persist restaurant update
        if (cart.getRestaurant() == null) {
            cart.setRestaurant(menuItem.getRestaurant());
            cartRepository.save(cart);   // ✅ THIS LINE WAS MISSING
        }
        
        // 🔹 VALIDATE SAME RESTAURANT
        Long cartRestaurantId = cart.getRestaurant() != null
                ? cart.getRestaurant().getRestaurantId()
                : null;

        Long newRestaurantId = menuItem.getRestaurant().getRestaurantId();

        if (cartRestaurantId != null && !cartRestaurantId.equals(newRestaurantId)) {
            throw new RuntimeException("Cannot add items from different restaurant");
        }

        // 🔹 CHECK EXISTING CART ITEM
        Optional<CartItem> existingItem =
                cartItemRepository.findByCart_CartIdAndMenuItem_MenuItemId(
                        cart.getCartId(), menuItemId);

        CartItem cartItem;

        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem = cartItemRepository.save(cartItem);
        } else {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .menuItem(menuItem)
                    .quantity(quantity)
                    .build();

            cartItem = cartItemRepository.save(cartItem);
        }

        // 🔹 ADDONS LOGIC
        if (addonIds != null && !addonIds.isEmpty()) {

            for (Long addonId : addonIds) {

                Addon addon = addonRepository.findById(addonId)
                        .orElseThrow(() -> new RuntimeException("Addon not found"));

                CartItemAddon cartAddon = CartItemAddon.builder()
                        .id(new CartItemAddonId(cartItem.getCartItemId(), addonId))
                        .cartItem(cartItem)
                        .addon(addon)
                        .build();

                cartItemAddonRepository.save(cartAddon);
            }
        }
    }
}