package com.avantesb.rfidbankmicroservice.sevice.helpers;

import com.avantesb.rfidbankmicroservice.exceptions.EntityNotFoundException;
import com.avantesb.rfidbankmicroservice.model.constant.TransactionType;
import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.entity.AccountEntity;
import com.avantesb.rfidbankmicroservice.model.entity.TransactionEntity;
import com.avantesb.rfidbankmicroservice.model.repository.AccountEntityRepository;
import com.avantesb.rfidbankmicroservice.model.repository.TransactionEntityRepository;
import com.avantesb.rfidbankmicroservice.model.request.TransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CardTransaction {

    private final AccountEntityRepository accountRepository;
    private final TransactionEntityRepository transactionRepository;

    @Autowired
    public CardTransaction(AccountEntityRepository accountRepository, TransactionEntityRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public String cashCardTransfer(AccountBank fromAccount, TransferRequest request) throws Exception {
        String transactionId = UUID.randomUUID().toString();

        AccountEntity fromAccountEntity = accountRepository.findByNumber(fromAccount.getNumber())
                .orElseThrow(EntityNotFoundException::new);

        fromAccountEntity.setActualBalance(fromAccountEntity.getActualBalance().add(request.getAmmount()));
        fromAccountEntity.setAvailableBalance(fromAccountEntity.getAvailableBalance().add(request.getAmmount()));
        accountRepository.save(fromAccountEntity);

        transactionRepository.save(TransactionEntity.builder().transactionType(TransactionType.CARD_TRANSFER)
                .account(fromAccountEntity)
                .transactionId(transactionId)
                .ammount(request.getAmmount())
                .transferId(request.getTransferId())
                .build());


        return transactionId;

    }

}
