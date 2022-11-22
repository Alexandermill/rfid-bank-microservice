package com.avantesb.rfidbankmicroservice.sevice;

import com.avantesb.rfidbankmicroservice.exceptions.EntityNotFoundException;
import com.avantesb.rfidbankmicroservice.exceptions.GlobalErrorCode;
import com.avantesb.rfidbankmicroservice.exceptions.InsufficientFundsException;
import com.avantesb.rfidbankmicroservice.model.constant.TransactionType;
import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.dto.UtilAccountBank;
import com.avantesb.rfidbankmicroservice.model.entity.AccountEntity;
import com.avantesb.rfidbankmicroservice.model.entity.TransactionEntity;
import com.avantesb.rfidbankmicroservice.model.repository.AccountEntityRepository;
import com.avantesb.rfidbankmicroservice.model.repository.TransactionEntityRepository;
import com.avantesb.rfidbankmicroservice.model.request.FundTransferRequest;
import com.avantesb.rfidbankmicroservice.model.request.UtilityPaymentRequest;
import com.avantesb.rfidbankmicroservice.model.response.FundTransferResponse;
import com.avantesb.rfidbankmicroservice.model.response.UtilityPaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionService {

    private AccountService accountService;
    private AccountEntityRepository accountRepository;
    private TransactionEntityRepository transactionRepository;



    public FundTransferResponse fundTransfer(FundTransferRequest transferRequest){
        AccountBank fromAccount = accountService.readAccount(transferRequest.getFromAccount());
        AccountBank toAccount = accountService.readAccount(transferRequest.getToAccount());

        validateBalance(fromAccount, transferRequest.getAmmount());

        String transactionId = internalFundTransfer(fromAccount, toAccount, transferRequest.getAmmount());
        return FundTransferResponse.builder().message("Transaction successfully completed").transactionId(transactionId)
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

    private void validateBalance(AccountBank accountBank, BigDecimal ammount){
        if(accountBank.getAvailableBalance().compareTo(BigDecimal.ZERO) < 0 || accountBank.getAvailableBalance().compareTo(ammount) < 0){
            throw new InsufficientFundsException("Insufficient funds in the account " + accountBank.getNumber(), GlobalErrorCode.INSUFFICIENT_FUNDS);
        }
    }

    public String internalFundTransfer(AccountBank fromAccount, AccountBank toAccount, BigDecimal ammount){
        String transactionId = UUID.randomUUID().toString();

        AccountEntity fromAccountEntity = accountRepository.findByNumber(fromAccount.getNumber())
                .orElseThrow(EntityNotFoundException::new);
        AccountEntity toAccountEntity = accountRepository.findByNumber(toAccount.getNumber())
                .orElseThrow(EntityNotFoundException::new);

        fromAccountEntity.setActualBalance(fromAccountEntity.getActualBalance().subtract(ammount));
        fromAccountEntity.setAvailableBalance(fromAccountEntity.getAvailableBalance().subtract(ammount));
        accountRepository.save(fromAccountEntity);

        transactionRepository.save(TransactionEntity.builder().transactionType(TransactionType.FUND_TRANSFER)
                .referenceNumber(toAccountEntity.getNumber())
                .account(fromAccountEntity)
                .transactionId(transactionId)
                .ammount(ammount.negate())
                .build());

        toAccountEntity.setAvailableBalance(toAccountEntity.getAvailableBalance().add(ammount));
        toAccountEntity.setActualBalance(toAccountEntity.getActualBalance().add(ammount));
        accountRepository.save(toAccountEntity);

        transactionRepository.save(TransactionEntity.builder().transactionType(TransactionType.FUND_TRANSFER)
                .referenceNumber(toAccountEntity.getNumber())
                .account(fromAccountEntity)
                .transactionId(transactionId)
                .ammount(ammount)
                .build());

        return transactionId;

    }

}
