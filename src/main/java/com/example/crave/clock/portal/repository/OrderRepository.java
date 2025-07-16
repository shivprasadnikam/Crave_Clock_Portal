package com.example.crave.clock.portal.repository;

import com.example.crave.clock.portal.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    // Custom queries (if needed) can be defined here
    List<OrderEntity> findByUserIdOrderByOrderDateDesc(Long userId);
}
