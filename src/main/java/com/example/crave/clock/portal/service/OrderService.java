package com.example.crave.clock.portal.service;

import com.example.crave.clock.portal.dto.OrderPlacedResponseDTO;
import com.example.crave.clock.portal.dto.OrderRequestDTO;
import com.example.crave.clock.portal.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderPlacedResponseDTO processOrder(OrderRequestDTO orderRequest);
     List<OrderEntity> getOrdersByUserId(Long userId);
}
