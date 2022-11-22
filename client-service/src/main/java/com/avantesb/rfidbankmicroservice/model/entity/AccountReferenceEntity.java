package com.avantesb.rfidbankmicroservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "account_ref")
public class AccountReferenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String number;

    @ManyToOne
    @JoinColumn(name = "client_id")
    ClientBankEntity client;

    public AccountReferenceEntity() {
    }

    public AccountReferenceEntity(String number, ClientBankEntity client) {
        this.number = number;
        this.client = client;
    }

}
