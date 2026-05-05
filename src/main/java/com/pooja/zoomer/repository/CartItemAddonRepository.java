package com.pooja.zoomer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pooja.zoomer.entity.*;

@Repository
public interface CartItemAddonRepository extends JpaRepository<CartItemAddon, CartItemAddonId> {
}