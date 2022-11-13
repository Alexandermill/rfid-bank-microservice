package com.avantesb.rfidbankmicroservice.model.dto;

import com.avantesb.rfidbankmicroservice.model.constant.AccountStatus;
import com.avantesb.rfidbankmicroservice.model.constant.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountBank {
    private Long id;
    private String number;
    private AccountType type;
    private AccountStatus status;
    private BigDecimal availableBalance;
    private BigDecimal actualBalance;
    private ClientBank client;
}
