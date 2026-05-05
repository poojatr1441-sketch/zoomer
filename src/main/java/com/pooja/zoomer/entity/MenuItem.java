package com.pooja.zoomer.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pooja.zoomer.entity.enums.Category;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id")
    private Long menuItemId;

    // 🔹 Prevent infinite loop
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Restaurant restaurant;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private BigDecimal price;

    // 🔹 IMPORTANT: Use Boolean (not boolean)
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    // 🔹 Addons (for later use)
    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItemAddon> menuItemAddons;
}