package com.avantesb.rfidbankmicroservice.model.entity;

import com.avantesb.rfidbankmicroservice.model.constant.AccountStatus;
import com.avantesb.rfidbankmicroservice.model.constant.AccountType;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import lombok.ToString;

import java.math.BigDecimal;

@ToString // TODO: 02.12.2022 Убрать потом
@Entity
@Getter
@Setter
@Table(name = "bank_account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String number;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private BigDecimal availableBalance;

    private BigDecimal actualBalance;

    private Long clientId;

}
