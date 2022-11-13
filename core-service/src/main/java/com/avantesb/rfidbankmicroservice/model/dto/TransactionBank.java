package com.avantesb.rfidbankmicroservice.model.dto;

import com.avantesb.rfidbankmicroservice.model.constant.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionBank {
    private Long id;
    private BigDecimal ammount;
    private TransactionType transactionType;
    private String referenceNumber;
    private String transactionId;
    private AccountBank account;
}
