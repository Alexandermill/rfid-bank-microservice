package com.avantesb.rfidbankmicroservice.model.entity;

import com.avantesb.rfidbankmicroservice.model.TransactionStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "fund_transfer")
@ToString
public class FundTransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String transactionReference;
    String fromAccount;
    String toAccount;
    private BigDecimal ammount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
