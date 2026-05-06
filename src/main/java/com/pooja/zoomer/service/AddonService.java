package com.pooja.zoomer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.pooja.zoomer.entity.Addon;
import com.pooja.zoomer.repository.AddonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddonService {

    private final AddonRepository addonRepository;

    public Addon createAddon(Addon addon) {
        return addonRepository.save(addon);
    }

    public List<Addon> getAllAddons() {
        return addonRepository.findAll();
    }
}