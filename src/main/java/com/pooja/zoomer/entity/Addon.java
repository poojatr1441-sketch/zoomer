package com.pooja.zoomer.entity;


import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addon")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Addon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "addon_id")
	private Long addonId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "addon", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MenuItemAddon> menuItemAddons;

}
