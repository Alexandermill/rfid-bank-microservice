package com.avantesb.rfidbankmicroservice.model.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private Long transferId;
    private String fromAccount;
    private String toAccount;
    private BigDecimal ammount;
}
