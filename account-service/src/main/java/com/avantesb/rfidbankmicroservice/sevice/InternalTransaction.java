package com.avantesb.rfidbankmicroservice.sevice;

import com.avantesb.rfidbankmicroservice.exceptions.EntityNotFoundException;
import com.avantesb.rfidbankmicroservice.model.constant.TransactionType;
import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.entity.AccountEntity;
import com.avantesb.rfidbankmicroservice.model.entity.TransactionEntity;
import com.avantesb.rfidbankmicroservice.model.repository.AccountEntityRepository;
import com.avantesb.rfidbankmicroservice.model.repository.TransactionEntityRepository;
import com.avantesb.rfidbankmicroservice.model.request.TransferRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InternalTransaction {

    private AccountEntityRepository accountRepository;
    private TransactionEntityRepository transactionRepository;

    public String internalFundTransfer(AccountBank fromAccount, AccountBank toAccount, BigDecimal ammount, TransferRequest request){
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
                .transferId(request.getTransferId())
                .build());

        toAccountEntity.setAvailableBalance(toAccountEntity.getAvailableBalance().add(ammount));
        toAccountEntity.setActualBalance(toAccountEntity.getActualBalance().add(ammount));
        accountRepository.save(toAccountEntity);

        transactionRepository.save(TransactionEntity.builder().transactionType(TransactionType.FUND_TRANSFER)
                .referenceNumber(toAccountEntity.getNumber())
                .account(toAccountEntity)
                .transactionId(transactionId)
                .ammount(ammount)
                .transferId(request.getTransferId())
                .build());

        return transactionId;

    }


}
