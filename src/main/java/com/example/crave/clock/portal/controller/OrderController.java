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
        log.info("[OrderController] placeOrder called for userId={}, itemsCount={}", orderRequest.getUserId(),
                orderRequest.getItems() != null ? orderRequest.getItems().size() : 0);
        try {
            OrderPlacedResponseDTO orderPlacedResponseDTO = orderService.processOrder(orderRequest);
            log.info("[OrderController] placeOrder result: orderId={}", orderPlacedResponseDTO.getOrderId());
            return orderPlacedResponseDTO;
        } catch (Exception e) {
            log.error("[OrderController] placeOrder error: {}", e.getMessage(), e);
        }
        return new OrderPlacedResponseDTO();
    }

    @GetMapping("/api/orders/user/{userId}")
    public ResponseEntity<List<OrderEntity>> getOrderHistory(@PathVariable Long userId) {
        log.info("[OrderController] getOrderHistory called for userId={}", userId);
        List<OrderEntity> orders = orderService.getOrdersByUserId(userId);
        log.info("[OrderController] getOrderHistory result: {} orders", orders.size());
        return ResponseEntity.ok(orders);
    }
}
