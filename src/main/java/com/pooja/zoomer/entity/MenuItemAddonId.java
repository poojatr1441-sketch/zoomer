package com.pooja.zoomer.entity;


import lombok.*;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode //Required for composite key correctness

public class MenuItemAddonId implements Serializable {

	@Column(name = "menu_item_id")
	private Long menuItemId;
	
	@Column(name = "addon_id")
	private Long addonId;
}
