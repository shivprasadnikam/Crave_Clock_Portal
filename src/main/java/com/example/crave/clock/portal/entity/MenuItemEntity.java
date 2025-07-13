package com.example.crave.clock.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "CC_MENU_ITEMS")
public class MenuItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(name = "VENDOR_ID")
    private Long vendorId;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "IS_AVAILABLE")
    private String isAvailable;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    // Optional: lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
