package com.example.crave.clock.portal.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long cartItemId;
    private Long userId;
    private Long vendorId;
    private Long menuId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
