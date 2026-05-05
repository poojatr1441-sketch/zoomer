package com.pooja.zoomer.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pooja.zoomer.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final OrderService orderService;

    @PutMapping("/order/{orderId}/assign/{agentId}")
    public String assignAgent(@PathVariable Long orderId,
                              @PathVariable Long agentId) {

        orderService.assignAgent(orderId, agentId);
        return "Agent assigned";
    }

    @PutMapping("/order/{orderId}/pickup")
    public String pickup(@PathVariable Long orderId) {

        orderService.pickupOrder(orderId);
        return "Order picked up";
    }

    @PutMapping("/order/{orderId}/deliver")
    public String deliver(@PathVariable Long orderId) {

        orderService.deliverOrder(orderId);
        return "Order delivered";
    }
}