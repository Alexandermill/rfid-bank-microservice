package com.avantesb.rfidbankmicroservice.sevice.message;


import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.entity.IdempotencyKey;
import com.avantesb.rfidbankmicroservice.model.repository.IdempKeyRepository;
import com.avantesb.rfidbankmicroservice.model.request.TransferRequest;
import com.avantesb.rfidbankmicroservice.model.response.TransferResponse;
import com.avantesb.rfidbankmicroservice.sevice.AccountService;
import com.avantesb.rfidbankmicroservice.sevice.TransactionService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Component
public class MessageService {

    @Value("${idempotencyKey.ttl:120}")
    private Long IDEMPOTENCY_KEY_TTL;

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final IdempKeyRepository idempKeyRepository;

    public MessageService(AccountService accountService, TransactionService transactionService, IdempKeyRepository idempKeyRepository) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.idempKeyRepository = idempKeyRepository;
    }

    @Bean
    Function<List<Long>, List<AccountBank>> getAccounts(){
        return input -> accountService.getAccountsByClientId(input);
    }

    @Bean
    Function<Message<TransferRequest>, TransferResponse > commitTransaction(){
        return message -> {
            System.out.println("in Reddis: \n"+ idempKeyRepository.findAll());
            System.out.println("MESSAGE:\n"+message);
            Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
            Long deliveryTag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);

            String key = message.getHeaders().get("Idempotency-Key", String.class);
            Optional<IdempotencyKey> idempotencyKeyOptional = idempKeyRepository.findById(key);
            if(idempotencyKeyOptional.isPresent()){
                System.out.println("Найден Idempotency-Key. Отдаю трансакцию из Базы");
//                return TransferResponse.builder()
//                        .transferId(message.getPayload().getTransferId())
//                        .message("Transaction has already been completed")
//                        .build();
                ackMessage(channel, deliveryTag);
                return transactionService.getSavedTransaction(message.getPayload());
            }

            TransferResponse response = transactionService.fundTransfer(message.getPayload());
            IdempotencyKey idempotencyKey = IdempotencyKey.builder()
                    .key(key)
                    .request(message.getPayload())
                    .expiration(IDEMPOTENCY_KEY_TTL)
                    .build();
            idempKeyRepository.save(idempotencyKey);

            ackMessage(channel, deliveryTag);

            return response;
        };
    }

    private void ackMessage(Channel channel, Long deliveryTag){
        try {
            channel.basicAck(deliveryTag, false);
            System.out.println("============Acknoledge==============");
        } catch (Exception e) {
            log.info("Failed to ack original message", e);
        }
    }
}
