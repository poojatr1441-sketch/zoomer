package com.pooja.zoomer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.pooja.zoomer.entity.Addon;
import com.pooja.zoomer.service.AddonService;

import java.util.List;

@RestController
@RequestMapping("/addon")
@RequiredArgsConstructor
public class AddonController {

    private final AddonService addonService;

    // 🔹 CREATE ADDON
    @PostMapping
    public Addon createAddon(@RequestBody Addon addon) {
        return addonService.createAddon(addon);
    }

    // 🔹 GET ALL ADDONS
    @GetMapping
    public List<Addon> getAllAddons() {
        return addonService.getAllAddons();
    }
}