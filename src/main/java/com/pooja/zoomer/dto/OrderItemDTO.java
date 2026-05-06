package com.pooja.zoomer.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDTO {

    private String itemName;
    private int quantity;
    private BigDecimal price;
}