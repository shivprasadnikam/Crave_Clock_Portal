package com.example.crave.clock.portal.entity;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CC_ORDERS")
@Data
public class OrderEntity {

    @Id
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "DELIVERY_ADDRESS")
    private String deliveryAddress;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "SPECIAL_INSTRUCTIONS")
    private String specialInstructions;

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "ORDER_DATE")
    private LocalDateTime orderDate;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

}
