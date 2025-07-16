package com.example.crave.clock.portal.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderPlacedResponseDTO {
    private String orderId;
    private Long userId;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private String deliveryAddress;
    private String status;
    private List<OrderItemDTO> items;
}
