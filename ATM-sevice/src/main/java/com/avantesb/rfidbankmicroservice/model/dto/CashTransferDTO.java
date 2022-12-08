package com.avantesb.rfidbankmicroservice.model.dto;

import com.avantesb.rfidbankmicroservice.model.TransactionStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
public class CashTransferDTO {

    private UUID id;
    private String cardNumber;
    private BigDecimal ammount;
    private TransactionStatus status;


}
