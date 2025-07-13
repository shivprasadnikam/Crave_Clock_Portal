package com.example.crave.clock.portal.repository;

import com.example.crave.clock.portal.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> findByVendorId(Long vendorId);
    List<MenuItemEntity> findByCategory(String category);
    List<MenuItemEntity> findByIsAvailable(String isAvailable); // 'Y' or 'N'
}
