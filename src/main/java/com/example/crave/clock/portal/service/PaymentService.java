package com.example.crave.clock.portal.service;

import com.example.crave.clock.portal.dto.PaymentRequestDTO;
import com.example.crave.clock.portal.dto.PaymentInitiationResponse;

public interface PaymentService {
    PaymentInitiationResponse initiatePayment(PaymentRequestDTO paymentRequestDTO);

    void updatePaymentStatus(Long paymentId, String status, String transactionId);

    String getPaymentStatus(Long paymentId);
}