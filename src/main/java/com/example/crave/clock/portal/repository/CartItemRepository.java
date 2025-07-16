package com.example.crave.clock.portal.repository;

import com.example.crave.clock.portal.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByUserId(Long userId);
    List<CartItemEntity> findByUserIdAndVendorId(Long userId, Long vendorId);
}
