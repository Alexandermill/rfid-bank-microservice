package com.avantesb.rfidbankmicroservice.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UtilityPaymentRequest {
    private Long providerId;
    private String referenceNumber;
    private BigDecimal ammount;
    private String account;
}
