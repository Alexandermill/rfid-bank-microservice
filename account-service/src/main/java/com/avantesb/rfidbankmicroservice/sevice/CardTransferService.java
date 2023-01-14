package com.avantesb.rfidbankmicroservice.sevice;

import com.avantesb.rfidbankmicroservice.exceptions.EntityNotFoundException;
import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.entity.AccountEntity;
import com.avantesb.rfidbankmicroservice.model.repository.AccountEntityRepository;
import com.avantesb.rfidbankmicroservice.model.request.CardTransferRequest;
import com.avantesb.rfidbankmicroservice.model.request.CashTransferRequest;
import com.avantesb.rfidbankmicroservice.model.response.TransferResponse;
import com.avantesb.rfidbankmicroservice.model.response.TransferResponseUUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CardTransferService {

    private final AccountEntityRepository accountRepository;

    public TransferResponseUUID cardTransfer(CardTransferRequest request){
        String transactionId = UUID.randomUUID().toString();

        if(!accountExist(request.getAccountNumber())){
            log.info("Transfer ID: {} failed. Account from {} not found", request.getTransferId(), request.getAccountNumber());
            return TransferResponseUUID.builder().message("Transaction failed. Account from not found")
                    .transferId(request.getTransferId())
                    .build();
        }


        return null;
    }

    private boolean validateBalance(AccountBank accountBank, BigDecimal ammount){
        if(accountBank.getAvailableBalance().compareTo(BigDecimal.ZERO) < 0 || accountBank.getAvailableBalance().compareTo(ammount) < 0){
            return false;
        }
        return true;
    }

    private boolean accountExist(String accountNumber){
        Optional<AccountEntity> entity = accountRepository.findByNumber(accountNumber);
        return entity.isPresent();
    }

}
