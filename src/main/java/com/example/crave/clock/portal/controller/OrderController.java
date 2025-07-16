package com.example.crave.clock.portal.controller;

import com.example.crave.clock.portal.dto.OrderPlacedResponseDTO;
import com.example.crave.clock.portal.dto.OrderRequestDTO;
import com.example.crave.clock.portal.entity.OrderEntity;
import com.example.crave.clock.portal.service.OrderService;
import com.example.crave.clock.portal.util.OrderUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/api/orders")
    public OrderPlacedResponseDTO placeOrder(@RequestBody OrderRequestDTO orderRequest) {
        try {
            log.info("Order Created");
            OrderPlacedResponseDTO orderPlacedResponseDTO = orderService.processOrder(orderRequest);
            return orderPlacedResponseDTO;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new OrderPlacedResponseDTO();
    }
    @GetMapping("/api/orders/user/{userId}")
    public ResponseEntity<List<OrderEntity>> getOrderHistory(@PathVariable Long userId) {
        log.info("Order History Called");
        List<OrderEntity> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}

