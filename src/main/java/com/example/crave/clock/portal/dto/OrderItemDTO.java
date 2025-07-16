package com.example.crave.clock.portal.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long menuId;
    private Long vendorId;
    private Integer quantity;
    private BigDecimal price;
}
