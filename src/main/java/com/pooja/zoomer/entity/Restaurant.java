package com.pooja.zoomer.entity;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restaurant_id")
	private Long restaurantId;
	
	@Column(nullable = false)
	private String name;
	
	@ToString.Exclude
    @EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
	@JsonBackReference
	private User owner;
	
	@ToString.Exclude
    @EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
	@JsonIgnore
	private Address address;
	
	@Column(name = "is_open",nullable = false)
	private Boolean isOpen;
	
	//@Column(nullable = false)
	private BigDecimal rating;
	
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MenuItem> menuItems;
}
