package com.avantesb.rfidbankmicroservice.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferRequest {
    private String transferId;
    private String fromAccount;
    private String toAccount;
    private BigDecimal ammount;
}
