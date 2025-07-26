package com.example.crave.clock.portal.repository;

import com.example.crave.clock.portal.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    @Query(value = "SELECT nextval('order_item_id_seq')", nativeQuery = true)
    Long getOrderItemId();
}

