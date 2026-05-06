package com.pooja.zoomer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pooja.zoomer.entity.MenuItemAddon;
import com.pooja.zoomer.entity.MenuItemAddonId;

public interface MenuItemAddonRepository extends JpaRepository<MenuItemAddon, MenuItemAddonId> {}
