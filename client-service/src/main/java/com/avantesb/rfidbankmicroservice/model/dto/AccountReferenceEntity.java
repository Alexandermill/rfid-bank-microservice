package com.avantesb.rfidbankmicroservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "account_ref")
public class AccountReferenceEntity {

    Long clientId;
    Long accountId;

}
