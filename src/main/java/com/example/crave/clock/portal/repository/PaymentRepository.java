package com.example.crave.clock.portal.repository;

import com.example.crave.clock.portal.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}