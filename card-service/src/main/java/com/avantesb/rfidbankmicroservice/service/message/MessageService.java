package com.avantesb.rfidbankmicroservice.service.message;

import com.avantesb.rfidbankmicroservice.model.dto.request.CardTransferRequest;
import com.avantesb.rfidbankmicroservice.model.dto.request.CashTransferRequest;
import com.avantesb.rfidbankmicroservice.model.dto.request.TransferRequest;
import com.avantesb.rfidbankmicroservice.model.dto.response.CardTransferResponse;
import com.avantesb.rfidbankmicroservice.service.CardService;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class MessageService {

    private final CardService cardService;

    public MessageService(CardService cardService) {
        this.cardService = cardService;
    }

    @Bean
    Function<Message<CashTransferRequest>, Message<TransferRequest>> cardTransfer(){
        return input -> {
            return MessageBuilder
                    .withPayload(cardService.cardTransfer(input.getPayload()))
                    .setHeader("Idempotency-Key", input.getHeaders().get("Idempotency-Key"))
                    .build();
        };
    }

    @Bean
    Consumer<CardTransferResponse> updateCardTransfer(){
        return input -> cardService.updateTransfer(input);
    }

}
