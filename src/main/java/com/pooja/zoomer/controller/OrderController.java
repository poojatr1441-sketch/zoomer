package com.pooja.zoomer.controller;

import com.pooja.zoomer.entity.Order;
import com.pooja.zoomer.entity.enums.PaymentMethod;
import com.pooja.zoomer.service.OrderService;
import com.pooja.zoomer.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    // 🔹 PLACE ORDER
    @PostMapping("/place")
    public String placeOrder(@RequestParam Long userId,
                             @RequestParam Long addressId,
                             @RequestParam PaymentMethod method) {

        orderService.placeOrder(userId, addressId, method);
        return "Order placed successfully";
    }

    // 🔹 TRACK ORDER
    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    // 🔹 CANCEL ORDER
    @PutMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "Order cancelled";
    }
}