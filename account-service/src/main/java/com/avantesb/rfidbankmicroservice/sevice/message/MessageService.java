package com.avantesb.rfidbankmicroservice.sevice.message;


import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.entity.IdempotencyKey;
import com.avantesb.rfidbankmicroservice.model.repository.IdempKeyRepository;
import com.avantesb.rfidbankmicroservice.model.request.TransferRequest;
import com.avantesb.rfidbankmicroservice.model.response.TransferResponse;
import com.avantesb.rfidbankmicroservice.sevice.AccountService;
import com.avantesb.rfidbankmicroservice.sevice.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class MessageService {

    private AccountService accountService;
    private TransactionService transactionService;
    private IdempKeyRepository idempKeyRepository;

    @Bean
    Function<List<Long>, List<AccountBank>> getAccounts(){
        return input -> accountService.getAccountsByClientId(input);
    }


//    @Bean
//    Function<TransferRequest, TransferResponse> commitTransaction(){
//        return request -> transactionService.fundTransfer(request);
//    }

    @Bean
    Function<Message<TransferRequest>, TransferResponse > commitTransaction(){
        return message -> {
            System.out.println("in Reddis: \n"+ idempKeyRepository.findAll());

            String key = message.getHeaders().get("Idempotency-Key", String.class);
            Optional<IdempotencyKey> idempotencyKeyOptional = idempKeyRepository.findById(key);
            if(idempotencyKeyOptional.isPresent()){
                System.out.println("Найден Idempotency-Key. Отдаю трансакцию из Базы");
//                return TransferResponse.builder()
//                        .transferId(message.getPayload().getTransferId())
//                        .message("Transaction has already been completed")
//                        .build();
                return transactionService.getSavedTransaction(message.getPayload());
            }
            TransferResponse response = transactionService.fundTransfer(message.getPayload());
            IdempotencyKey idempotencyKey = IdempotencyKey.builder()
                    .key(key)
                    .request(message.getPayload())
                    .expiration(120L)
                    .build();
            idempKeyRepository.save(idempotencyKey);
            return response;
        };
    }
}
