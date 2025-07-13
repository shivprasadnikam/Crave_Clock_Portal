package com.example.crave.clock.portal.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class MenuItemDTO {
    private Long menuId;
    private Long vendorId;
    private String itemName;
    private String description;
    private BigDecimal price;
    private String category;
    private String isAvailable;

    // Getters and Setters
}
