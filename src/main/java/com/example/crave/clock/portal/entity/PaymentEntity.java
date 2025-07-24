package com.example.crave.clock.portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CC_PAYMENTS")
@Data
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cc_payments_seq_gen")
    @SequenceGenerator(name = "cc_payments_seq_gen", sequenceName = "CC_PAYMENTS_SEQ", allocationSize = 1)
    @Column(name = "PAYMENT_ID")
    private Long paymentId;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name = "PAYMENT_STATUS")
    private String paymentStatus;

    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "UPI_COLLECT_URL")
    private String upiCollectUrl;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
}