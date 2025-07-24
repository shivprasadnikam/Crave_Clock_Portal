package com.example.crave.clock.portal.serviceImpl;

import com.example.crave.clock.portal.dto.OrderItemDTO;
import com.example.crave.clock.portal.dto.OrderPlacedResponseDTO;
import com.example.crave.clock.portal.dto.OrderRequestDTO;
import com.example.crave.clock.portal.entity.OrderEntity;
import com.example.crave.clock.portal.entity.OrderItemEntity;
import com.example.crave.clock.portal.entity.OnboardedUserEntity;
import com.example.crave.clock.portal.repository.OrderItemRepository;
import com.example.crave.clock.portal.repository.OrderRepository;
import com.example.crave.clock.portal.repository.RcUserDetailsRepository;
import com.example.crave.clock.portal.service.ExpoPushNotificationService;
import com.example.crave.clock.portal.service.OrderService;
import com.example.crave.clock.portal.util.OrderUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ExpoPushNotificationService expoPushNotificationService;

    @Autowired
    private RcUserDetailsRepository rcUserDetailsRepository;

    @Override
    @Transactional
    public OrderPlacedResponseDTO processOrder(OrderRequestDTO dto) {
        log.info("[OrderServiceImpl] processOrder called for userId={}, itemsCount={}", dto.getUserId(),
                dto.getItems() != null ? dto.getItems().size() : 0);
        OrderPlacedResponseDTO orderPlacedResponseDTO = new OrderPlacedResponseDTO();
        Long currentOrderId = orderItemRepository.getOrderItemId();
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
        log.info("[OrderServiceImpl] Order entity saved: orderId={}", currentOrderId);

        // 2. Save Order Items
        for (OrderItemDTO item : dto.getItems()) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrderItemId(currentOrderId);
            orderItem.setMenuId(item.getMenuId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            orderItem.setTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

            orderItemRepository.save(orderItem);
            log.info("[OrderServiceImpl] OrderItem entity saved: orderItemId={}, menuId={}", currentOrderId,
                    item.getMenuId());
        }
        orderPlacedResponseDTO.setOrderId(OrderUtility.generateOrderId());
        log.info("[OrderServiceImpl] processOrder completed: generatedOrderId={}", orderPlacedResponseDTO.getOrderId());

        // --- Push Notification Logic ---
        OnboardedUserEntity user = rcUserDetailsRepository.findById(dto.getUserId()).orElse(null);
        if (user != null && user.getExpoPushToken() != null && !user.getExpoPushToken().isEmpty()) {
            String expoPushToken = user.getExpoPushToken();
            String title = "Order Placed";
            String body = "Your order has been placed successfully!";
            expoPushNotificationService.sendPushNotification(expoPushToken, title, body, null);
        } else {
            log.warn("[OrderServiceImpl] Expo push token not found for userId={}", dto.getUserId());
        }
        // --- End Push Notification Logic ---

        return orderPlacedResponseDTO;
    }

    public List<OrderEntity> getOrdersByUserId(Long userId) {
        log.info("[OrderServiceImpl] getOrdersByUserId called for userId={}", userId);
        List<OrderEntity> orders = orderRepository.findByUserIdOrderByOrderDateDesc(userId);
        log.info("[OrderServiceImpl] getOrdersByUserId result: {} orders", orders.size());
        return orders;
    }
}
