package com.pooja.zoomer.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pooja.zoomer.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/user/{id}/approve")
    public String approveUser(@PathVariable Long id) {
        adminService.approveUser(id);
        return "User approved";
    }

    @PutMapping("/user/{id}/suspend")
    public String suspendUser(@PathVariable Long id) {
        adminService.suspendUser(id);
        return "User suspended";
    }
}