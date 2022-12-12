package com.avantesb.rfidbankmicroservice.model.entity;

import com.avantesb.rfidbankmicroservice.model.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "card_transaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String referenceId;
    private UUID transferId;
    private String cardNumber;
    private String accountNumber;
    private BigDecimal ammount;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
