package com.avantesb.rfidbankmicroservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    private String transferId;
    private String fromAccount;
    private String toAccount;
    private BigDecimal ammount;
}
