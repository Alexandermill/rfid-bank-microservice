package com.avantesb.rfidbankmicroservice.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String transferId;
    private String fromAccount;
    private String toAccount;
    private BigDecimal ammount;
}
