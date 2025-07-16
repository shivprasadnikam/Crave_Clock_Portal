package com.example.crave.clock.portal.entity;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CC_ORDER_ITEMS")
@Data
public class OrderItemEntity {

    @Id
    @Column(name = "ORDER_ITEM_ID")
    private Long orderItemId;

    @Column(name = "VENDOR_ID")
    private Long vendorId;

    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
}

