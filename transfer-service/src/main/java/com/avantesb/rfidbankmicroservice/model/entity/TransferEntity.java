package com.avantesb.rfidbankmicroservice.model.entity;

import com.avantesb.rfidbankmicroservice.model.TransactionStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@ToString // TODO: 02.12.2022
@Getter
@Setter
@Entity
@Table(name = "fund_transfer")
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID transferId;
    private String transactionReference;
    private String fromAccount;
    private String toAccount;
    private BigDecimal ammount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
