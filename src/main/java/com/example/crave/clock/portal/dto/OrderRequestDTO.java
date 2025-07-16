package com.example.crave.clock.portal.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequestDTO {
    private Long userId;
    private List<OrderItemDTO> items;
    private String deliveryAddress;
    private String phoneNumber;
    private String specialInstructions;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private String vendorId;
}

