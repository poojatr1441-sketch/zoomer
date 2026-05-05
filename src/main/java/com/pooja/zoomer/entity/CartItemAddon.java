package com.pooja.zoomer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_item_addon")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CartItemAddon {
    
	@EmbeddedId
	private CartItemAddonId id;
	
	@ToString.Exclude
    @EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "addon_id", nullable = false)
	@MapsId("addonId")
	private Addon addon;
	
	@ToString.Exclude
    @EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_item_id", nullable = false)
	@MapsId("cartItemId")
	private CartItem cartItem;
	
}
