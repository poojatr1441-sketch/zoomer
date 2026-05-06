package com.pooja.zoomer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_item_addon")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemAddon {
	
	@EmbeddedId
	private MenuItemAddonId id;
	
	@ToString.Exclude
    @EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("addonId")
	@JoinColumn(name = "addon_id", nullable = false)
	private Addon addon;
	
	@JsonIgnore
	@ToString.Exclude
    @EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("menuItemId")
	@JoinColumn(name = "menu_item_id", nullable = false)
	private MenuItem menuItem; 
	
}
