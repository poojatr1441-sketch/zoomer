package com.pooja.zoomer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.pooja.zoomer.entity.Address;
import com.pooja.zoomer.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public Address addAddress(@RequestParam Long userId,
                              @RequestBody Address address) {

        return addressService.addAddress(userId, address);
    }

    @GetMapping("/{userId}")
    public List<Address> getAddresses(@PathVariable Long userId) {
        return addressService.getUserAddresses(userId);
    }
}