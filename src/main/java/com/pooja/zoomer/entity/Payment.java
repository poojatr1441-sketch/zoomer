package com.pooja.zoomer.entity;

import java.math.BigDecimal;

import com.pooja.zoomer.entity.enums.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
	private Long paymentId;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false,unique = true)
    private Order order;
	
	@Column(name = "total_amount", nullable = false)
	private BigDecimal totalCost;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "method",nullable = false)
	private PaymentMethod method;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status",nullable = false)
	private PaymentStatus paymentStatus;
	
}
