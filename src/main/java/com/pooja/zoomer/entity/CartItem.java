package com.pooja.zoomer.entity;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "cart_item")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_item_id")
	private Long cartItemId;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id",nullable = false)
	private Cart cart;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_item_id",nullable = false)
	private MenuItem menuItem;

	@Column(name = "quantity", nullable = false)
	@Positive
	private Integer quantity;
	
	@OneToMany(mappedBy = "cartItem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItemAddon> cartItemAddons;

}
