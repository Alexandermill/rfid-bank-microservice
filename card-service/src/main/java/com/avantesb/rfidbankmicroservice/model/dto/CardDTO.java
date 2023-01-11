package com.avantesb.rfidbankmicroservice.model.dto;

import com.avantesb.rfidbankmicroservice.model.CardStstus;
import com.avantesb.rfidbankmicroservice.model.CardType;
import lombok.Data;

@Data
public class CardDTO {
    private Long id;
    private String cardNumber;
    private String accountNumber;
    private CardStstus cardStstus;
    private CardType cardType;

    public CardDTO() {
    }
}
