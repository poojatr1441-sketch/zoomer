package com.pooja.zoomer.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pooja.zoomer.service.OrderService;

import lombok.*;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OrderService orderService;

    @PutMapping("/order/{orderId}/accept")
    public String acceptOrder(@PathVariable Long orderId) {
        orderService.acceptOrder(orderId);
        return "Order accepted";
    }

    @PutMapping("/order/{orderId}/reject")
    public String rejectOrder(@PathVariable Long orderId) {
        orderService.rejectOrder(orderId);
        return "Order rejected";
    }
}