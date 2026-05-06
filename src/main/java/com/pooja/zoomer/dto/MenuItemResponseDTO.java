package com.pooja.zoomer.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuItemResponseDTO {

    private Long menuItemId;
    private String name;
    private String category;
    private Double price;
    private Boolean isAvailable;

    private List<AddonDTO> addons;
}