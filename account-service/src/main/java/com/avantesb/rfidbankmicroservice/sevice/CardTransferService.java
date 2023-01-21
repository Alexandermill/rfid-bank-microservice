package com.avantesb.rfidbankmicroservice.sevice;

import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.entity.AccountEntity;
import com.avantesb.rfidbankmicroservice.model.repository.AccountEntityRepository;
import com.avantesb.rfidbankmicroservice.model.request.TransferRequest;
import com.avantesb.rfidbankmicroservice.model.response.TransferResponse;
import com.avantesb.rfidbankmicroservice.sevice.helpers.CardTransaction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CardTransferService {

    private final AccountEntityRepository accountRepository;
    private final AccountService accountService;
    private final CardTransaction cardTransaction;

    public TransferResponse cardTransfer(TransferRequest request){
        String transactionId = UUID.randomUUID().toString();

        if(!accountExist(request.getFromAccount())){
            log.info("Transfer ID: {} failed. Account from {} not found", request.getTransferId(), request.getFromAccount());
            return TransferResponse.builder().message("Transaction failed. Account from not found")
                    .transferId(request.getTransferId())
                    .build();
        }

        AccountBank fromAccount = accountService.readAccount(request.getFromAccount());

        if(!validateBalance(fromAccount, request.getAmmount())){
            return TransferResponse.builder().message("Insufficient funds in the account " + fromAccount.getNumber())
                    .transferId(request.getTransferId())
                    .build();
        }

        try {
            cardTransaction.cashCardTransfer(fromAccount, request);
        } catch (Exception e) {
            e.printStackTrace(); // TODO: 21.01.2023 make exc handling
        }


        return TransferResponse.builder()
                .transferId(request.getTransferId())
                .message("Card Transfer successfully completed")
                .transactionId(transactionId)
                .build();
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
