package com.pooja.zoomer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddonDTO {

    private Long addonId;
    private String name;
    private Double price;
}