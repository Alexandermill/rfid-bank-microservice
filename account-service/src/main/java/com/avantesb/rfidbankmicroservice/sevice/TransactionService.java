package com.avantesb.rfidbankmicroservice.sevice;

import com.avantesb.rfidbankmicroservice.exceptions.EntityNotFoundException;
import com.avantesb.rfidbankmicroservice.model.constant.TransactionType;
import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.dto.UtilAccountBank;
import com.avantesb.rfidbankmicroservice.model.entity.AccountEntity;
import com.avantesb.rfidbankmicroservice.model.entity.TransactionEntity;
import com.avantesb.rfidbankmicroservice.model.repository.AccountEntityRepository;
import com.avantesb.rfidbankmicroservice.model.repository.TransactionEntityRepository;
import com.avantesb.rfidbankmicroservice.model.request.TransferRequest;
import com.avantesb.rfidbankmicroservice.model.request.UtilityPaymentRequest;
import com.avantesb.rfidbankmicroservice.model.response.TransferResponse;
import com.avantesb.rfidbankmicroservice.model.response.UtilityPaymentResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TransactionService {

    private AccountServiceImpl accountService;
    private AccountEntityRepository accountRepository;
    private TransactionEntityRepository transactionRepository;
    private InternalTransaction internalTransfer;



    public TransferResponse fundTransfer(TransferRequest transferRequest){

        if(!accountExist(transferRequest.getFromAccount())){
            log.info("Account from {} not found", transferRequest.getFromAccount());
            return TransferResponse.builder().message("Transaction failed. Account from not found")
                    .transferId(transferRequest.getTransferId())
                    .build();
        }

        if(!accountExist(transferRequest.getToAccount())){
            log.info("Account to {} not found", transferRequest.getToAccount());
            return TransferResponse.builder().message("Transaction failed. Account to not found")
                    .transferId(transferRequest.getTransferId())
                    .build();
        }

        AccountBank fromAccount = accountService.readAccount(transferRequest.getFromAccount());
        AccountBank toAccount = accountService.readAccount(transferRequest.getToAccount());

        if(!validateBalance(fromAccount, transferRequest.getAmmount())){
            return TransferResponse.builder().message("Insufficient funds in the account " + fromAccount.getNumber())
                    .transferId(transferRequest.getTransferId())
                    .build();
        }

        String transactionId = internalTransfer.internalFundTransfer(fromAccount, toAccount, transferRequest.getAmmount());

        return TransferResponse.builder().message("Transaction successfully completed")
                .transferId(transferRequest.getTransferId())
                .transactionId(transactionId)
                .build();

    }

    public UtilityPaymentResponse utilPayment(UtilityPaymentRequest utilityRequest){

        String transactionId = UUID.randomUUID().toString();
        AccountBank fromAccount = accountService.readAccount(utilityRequest.getAccount());

        validateBalance(fromAccount, utilityRequest.getAmmount());

        UtilAccountBank utilAccountBank = accountService.readUtilAccountById(utilityRequest.getProviderId());

        AccountEntity fromAccountEntity = accountRepository.findByNumber(fromAccount.getNumber()).orElseThrow(EntityNotFoundException::new);

        //we can call third party API to process UTIL payment from payment provider from here.

        fromAccountEntity.setActualBalance(fromAccountEntity.getActualBalance().subtract(utilityRequest.getAmmount()));
        fromAccountEntity.setAvailableBalance(fromAccountEntity.getAvailableBalance().subtract(utilityRequest.getAmmount()));
        accountRepository.save(fromAccountEntity);

        transactionRepository.save(TransactionEntity.builder().transactionType(TransactionType.UTILITY_PAYMENT)
                .account(fromAccountEntity)
                .transactionId(transactionId)
                .referenceNumber(utilityRequest.getReferenceNumber())
                .ammount(utilityRequest.getAmmount().negate())
                .build());

        return UtilityPaymentResponse.builder().message("Utility payment successfully completed")
                .transactionId(transactionId)
                .build();


    }

    private boolean validateBalance(AccountBank accountBank, BigDecimal ammount){
        if(accountBank.getAvailableBalance().compareTo(BigDecimal.ZERO) < 0 || accountBank.getAvailableBalance().compareTo(ammount) < 0){
//            throw new InsufficientFundsException("Insufficient funds in the account " + accountBank.getNumber(), GlobalErrorCode.INSUFFICIENT_FUNDS);
            return false;
        }
        return true;
    }

    private boolean accountExist(String accountNumber){
        Optional<AccountEntity> entity = accountRepository.findByNumber(accountNumber);
        return entity.isPresent();
    }



}
