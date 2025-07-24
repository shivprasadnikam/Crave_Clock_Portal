package com.example.crave.clock.portal.dto;

public class PaymentInitiationResponse {
    private Long paymentId;
    private String upiCollectUrl;

    public PaymentInitiationResponse(Long paymentId, String upiCollectUrl) {
        this.paymentId = paymentId;
        this.upiCollectUrl = upiCollectUrl;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getUpiCollectUrl() {
        return upiCollectUrl;
    }

    public void setUpiCollectUrl(String upiCollectUrl) {
        this.upiCollectUrl = upiCollectUrl;
    }
}