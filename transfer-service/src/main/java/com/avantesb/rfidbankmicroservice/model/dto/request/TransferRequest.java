package com.avantesb.rfidbankmicroservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    private Long transferId;
    private String fromAccount;
    private String toAccount;
    private BigDecimal ammount;
}
