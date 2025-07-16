package com.example.crave.clock.portal.serviceImpl;

import com.example.crave.clock.portal.dto.OrderItemDTO;
import com.example.crave.clock.portal.dto.OrderPlacedResponseDTO;
import com.example.crave.clock.portal.dto.OrderRequestDTO;
import com.example.crave.clock.portal.entity.OrderEntity;
import com.example.crave.clock.portal.entity.OrderItemEntity;
import com.example.crave.clock.portal.repository.OrderItemRepository;
import com.example.crave.clock.portal.repository.OrderRepository;
import com.example.crave.clock.portal.service.OrderService;
import com.example.crave.clock.portal.util.OrderUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public OrderPlacedResponseDTO processOrder(OrderRequestDTO dto) {
        OrderPlacedResponseDTO orderPlacedResponseDTO = new OrderPlacedResponseDTO();
        Long currentOrderId=orderItemRepository.getOrderItemId();
        // 1. Create and save Order entity
        OrderEntity order = new OrderEntity();
        order.setUserId(dto.getUserId());
        order.setOrderId(currentOrderId);
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setPhoneNumber(dto.getPhoneNumber());
        order.setSpecialInstructions("dto");
        order.setTotalAmount(dto.getTotalAmount());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
        log.info("Data saved into CC_ORDER");

        // 2. Save Order Items
        for (OrderItemDTO item : dto.getItems()) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrderItemId(currentOrderId);
            orderItem.setMenuId(item.getMenuId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            orderItem.setTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

            orderItemRepository.save(orderItem);
        }
        orderPlacedResponseDTO.setOrderId(OrderUtility.generateOrderId());
        log.info("Data saved into CC_ORDER_ITEMS");
        return orderPlacedResponseDTO;
    }

    public List<OrderEntity> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(userId);
    }
}

