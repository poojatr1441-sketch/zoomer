package com.pooja.zoomer.entity;

import jakarta.persistence.*;
import lombok.*;

@Embeddable

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode //Required for composite key correctness

public class CartItemAddonId {
	@Column(name = "cart_item_id")
	private Long cartItemId;
	
	@Column(name = "addon_id")
	private Long addonId;
}
