package com.avantesb.rfidbankmicroservice.model.entity;

import com.avantesb.rfidbankmicroservice.model.constant.TransactionType;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@Table(name = "bank_transaction")
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long transferId;

    private BigDecimal ammount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private String referenceNumber;
    private String transactionId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

}
