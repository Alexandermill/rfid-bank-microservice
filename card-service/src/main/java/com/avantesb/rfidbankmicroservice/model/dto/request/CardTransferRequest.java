package com.avantesb.rfidbankmicroservice.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CardTransferRequest {
    private UUID transferId;
    private String accountNumber;
    private BigDecimal ammount;
}
