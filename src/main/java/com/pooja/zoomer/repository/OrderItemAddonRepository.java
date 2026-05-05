package com.pooja.zoomer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pooja.zoomer.entity.OrderItemAddon;

@Repository
public interface OrderItemAddonRepository extends JpaRepository<OrderItemAddon, Long> {
}