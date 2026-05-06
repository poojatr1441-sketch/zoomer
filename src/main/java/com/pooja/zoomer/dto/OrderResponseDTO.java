package com.pooja.zoomer.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderResponseDTO {

    private Long orderId;
    private String status;
    private BigDecimal totalAmount;

    private String restaurantName;

    private List<OrderItemDTO> items;
}