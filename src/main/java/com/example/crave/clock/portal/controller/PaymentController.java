package com.example.crave.clock.portal.controller;

import com.example.crave.clock.portal.dto.PaymentRequestDTO;
import com.example.crave.clock.portal.dto.PaymentInitiationResponse;
import com.example.crave.clock.portal.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<PaymentInitiationResponse> initiate(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        log.info(
                "[API REQUEST] POST /api/payment/initiate - Initiating payment for userId={}, orderId={}, amount={}, method={}",
                paymentRequestDTO.getUserId(), paymentRequestDTO.getOrderId(), paymentRequestDTO.getAmount(),
                paymentRequestDTO.getPaymentMethod());
        PaymentInitiationResponse response = paymentService.initiatePayment(paymentRequestDTO);
        log.info("[API RESPONSE] Payment initiated: paymentId={}, upiCollectUrl={}", response.getPaymentId(),
                response.getUpiCollectUrl());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-status")
    public ResponseEntity<Void> updateStatus(@RequestParam Long paymentId, @RequestParam String status,
            @RequestParam(required = false) String transactionId) {
        log.info("[API REQUEST] POST /api/payment/update-status - paymentId={}, status={}, transactionId={}", paymentId,
                status, transactionId);
        paymentService.updatePaymentStatus(paymentId, status, transactionId);
        log.info("[API RESPONSE] Payment status updated for paymentId={}", paymentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{paymentId}")
    public ResponseEntity<String> getStatus(@PathVariable Long paymentId) {
        log.info("[API REQUEST] GET /api/payment/status/{} - Checking payment status", paymentId);
        String status = paymentService.getPaymentStatus(paymentId);
        if (status == null) {
            log.warn("[API RESPONSE] Payment not found for paymentId={}", paymentId);
            return ResponseEntity.notFound().build();
        }
        log.info("[API RESPONSE] Payment status for paymentId={}: {}", paymentId, status);
        return ResponseEntity.ok(status);
    }

    // Legacy/simple pay endpoint
    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        log.info(
                "[API REQUEST] POST /api/payment/pay - Processing legacy/simple payment for userId={}, orderId={}, amount={}, method={}",
                paymentRequestDTO.getUserId(), paymentRequestDTO.getOrderId(), paymentRequestDTO.getAmount(),
                paymentRequestDTO.getPaymentMethod());
        boolean success = "SUCCESS"
                .equalsIgnoreCase(paymentService.initiatePayment(paymentRequestDTO).getPaymentId().toString());
        if (success) {
            log.info("[API RESPONSE] Legacy/simple payment successful for userId={}, orderId={}",
                    paymentRequestDTO.getUserId(), paymentRequestDTO.getOrderId());
            return ResponseEntity.ok("Payment successful");
        } else {
            log.warn("[API RESPONSE] Legacy/simple payment failed for userId={}, orderId={}",
                    paymentRequestDTO.getUserId(), paymentRequestDTO.getOrderId());
            return ResponseEntity.badRequest().body("Payment failed or invalid method");
        }
    }
}