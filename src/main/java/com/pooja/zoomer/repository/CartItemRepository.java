package com.pooja.zoomer.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pooja.zoomer.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCart_CartIdAndMenuItem_MenuItemId(Long cartId, Long menuItemId);

    List<CartItem> findByCart_CartId(Long cartId); // useful later

}