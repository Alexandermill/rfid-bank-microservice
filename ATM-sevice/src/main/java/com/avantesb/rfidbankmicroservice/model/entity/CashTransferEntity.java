package com.avantesb.rfidbankmicroservice.model.entity;

import com.avantesb.rfidbankmicroservice.model.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CashTransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    UUID id;

    private String cardNumber;
    private BigDecimal ammount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;


}
