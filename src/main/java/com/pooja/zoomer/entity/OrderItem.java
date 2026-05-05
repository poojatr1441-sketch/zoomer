package com.pooja.zoomer.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_price", nullable = false)
    private BigDecimal itemPrice;

    @Column(nullable = false)
    @Positive
    private Integer quantity;
}