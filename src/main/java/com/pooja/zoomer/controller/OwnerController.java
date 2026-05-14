package com.pooja.zoomer.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.pooja.zoomer.service.OrderService;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OrderService orderService;

    @PutMapping("/order/{orderId}/accept")
    public String acceptOrder(@PathVariable Long orderId,
                              Authentication authentication) {

        String email = authentication.getName();

        orderService.acceptOrder(orderId, email);

        return "Order accepted";
    }

    @PutMapping("/order/{orderId}/reject")
    public String rejectOrder(@PathVariable Long orderId,
                              Authentication authentication) {

        String email = authentication.getName();

        orderService.rejectOrder(orderId, email);

        return "Order rejected";
    }
}