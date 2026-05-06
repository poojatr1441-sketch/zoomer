package com.pooja.zoomer.entity;
import jakarta.persistence.*;

import java.util.List;

import com.pooja.zoomer.entity.enums.*;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "user_id")//camelCase
	private Long userId; // pk
	
	@Column(nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
	@Column(unique = true)
	private String phone;
	
	@Column(unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role; //(ENUM: CUSTOMER, OWNER, DELIVERY_AGENT, ADMIN)
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Address> addresses;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "owner")
	@JsonManagedReference
	private List<Restaurant> restaurants;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Cart cart;
	

}

