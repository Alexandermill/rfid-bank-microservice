package com.avantesb.rfidbankmicroservice.model.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UtilityPaymentRequest {
    private Long providerId;
    private String referenceNumber;
    private BigDecimal ammount;
    private String account;
}
