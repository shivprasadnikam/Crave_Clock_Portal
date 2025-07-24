package com.example.crave.clock.portal.serviceImpl;

import com.example.crave.clock.portal.dto.PaymentRequestDTO;
import com.example.crave.clock.portal.dto.PaymentInitiationResponse;
import com.example.crave.clock.portal.entity.PaymentEntity;
import com.example.crave.clock.portal.repository.PaymentRepository;
import com.example.crave.clock.portal.repository.AppPropertyRepository;
import com.example.crave.clock.portal.entity.AppProperty;
import com.example.crave.clock.portal.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AppPropertyRepository appPropertyRepository;

    @Override
    public PaymentInitiationResponse initiatePayment(PaymentRequestDTO dto) {
        log.info("[PaymentService] Initiating payment for userId={}, orderId={}, amount={}, method={}", dto.getUserId(),
                dto.getOrderId(), dto.getAmount(), dto.getPaymentMethod());
        PaymentEntity payment = new PaymentEntity();
        payment.setOrderId(dto.getOrderId());
        payment.setUserId(dto.getUserId());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPaymentStatus("INITIATED");
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
        if ("UPI".equalsIgnoreCase(dto.getPaymentMethod())) {
            payment.setUpiCollectUrl(
                    "upi://pay?pa=dummyupi@bank&pn=Dummy+Merchant&am=" + dto.getAmount() + "&tr=" + dto.getOrderId());
            log.info("[PaymentService] Generated dummy UPI collect URL: {}", payment.getUpiCollectUrl());
        }
        paymentRepository.save(payment);
        log.info("[PaymentService] Payment record created with paymentId={}", payment.getPaymentId());
        // Fetch bypass flag from properties table
        AppProperty bypassFlag = appPropertyRepository.findByPropertyKey("bypass_payment");
        boolean bypassPayment = bypassFlag != null && "true".equalsIgnoreCase(bypassFlag.getPropertyValue());
        log.info("[PaymentService] bypass_payment flag is {}", bypassPayment);
        if (bypassPayment) {
            payment.setPaymentStatus("SUCCESS");
            payment.setTransactionId("CRAVE_CLOCK-TXN-" + payment.getPaymentId());
            payment.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(payment);
            log.info("[PaymentService] Payment bypassed and marked as SUCCESS for paymentId={}",
                    payment.getPaymentId());
        }
        return new PaymentInitiationResponse(payment.getPaymentId(), payment.getUpiCollectUrl());
    }

    @Override
    public void updatePaymentStatus(Long paymentId, String status, String transactionId) {
        PaymentEntity payment = paymentRepository.findById(paymentId).orElseThrow();
        log.info("[PaymentService] Updating payment status for paymentId={}, newStatus={}, transactionId={}", paymentId,
                status, transactionId);
        payment.setPaymentStatus(status);
        payment.setTransactionId(transactionId);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
        log.info("[PaymentService] Payment status updated for paymentId={}", paymentId);
    }

    @Override
    public String getPaymentStatus(Long paymentId) {
        PaymentEntity payment = paymentRepository.findById(paymentId).orElse(null);
        return payment != null ? payment.getPaymentStatus() : null;
    }
}