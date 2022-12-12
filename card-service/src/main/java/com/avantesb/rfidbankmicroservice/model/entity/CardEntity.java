package com.avantesb.rfidbankmicroservice.model.entity;

import com.avantesb.rfidbankmicroservice.model.CardStstus;
import com.avantesb.rfidbankmicroservice.model.CardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String cardNumber;
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private CardStstus cardStstus;

    @Enumerated(EnumType.STRING)
    private CardType cardType;


}
