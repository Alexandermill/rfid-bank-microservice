package com.avantesb.rfidbankmicroservice.model.dto;

import com.avantesb.rfidbankmicroservice.model.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FundTransfer {
    private Long id;
    private String transactionReference;
    private TransactionStatus status;
    private String fromAccount;
    private String toAccount;
    private BigDecimal ammount;
}
