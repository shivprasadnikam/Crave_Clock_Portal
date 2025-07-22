package com.example.crave.clock.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "CC_CART_ITEMS")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_item_seq_gen")
    @SequenceGenerator(name = "cart_item_seq_gen", sequenceName = "SEQ_CART_ITEM_ID", allocationSize = 1)
    @Column(name = "CART_ITEM_ID")
    private Long cartItemId;

    @Column(name = "USER_ID")
    private Long userId;

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
