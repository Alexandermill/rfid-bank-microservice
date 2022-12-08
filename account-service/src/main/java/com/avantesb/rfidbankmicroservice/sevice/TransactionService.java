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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class TransactionService {

    private AccountService accountService;
    private AccountEntityRepository accountRepository;
    private TransactionEntityRepository transactionRepository;
    private InternalTransaction internalTransfer;

    @Autowired
    public TransactionService(AccountService accountService, AccountEntityRepository accountRepository, TransactionEntityRepository transactionRepository, InternalTransaction internalTransfer) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.internalTransfer = internalTransfer;
    }


    public TransferResponse fundTransfer(TransferRequest transferRequest) {
        System.out.println("=================START Transaction SERVICE=================");
        if(!accountExist(transferRequest.getFromAccount())){
            log.info("Transfer ID: {} failed. Account from {} not found", transferRequest.getTransferId(), transferRequest.getFromAccount());
            return TransferResponse.builder().message("Transaction failed. Account from not found")
                    .transferId(transferRequest.getTransferId())
                    .build();
        }

        if(!accountExist(transferRequest.getToAccount())){
            log.info("Transfer ID: {} failed. Account to {} not found", transferRequest.getTransferId(), transferRequest.getToAccount());
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

        String transactionId = null;
        try {
            transactionId = internalTransfer.internalFundTransfer(fromAccount, toAccount, transferRequest.getAmmount(), transferRequest );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(transferRequest.getAmmount().doubleValue() == 101){
            System.out.println("!!!!!!!!!!!FAILED TRANSFER");
            throw new RuntimeException("Ooops");
        }
        log.info(" Transaction ID: {} success", transactionId);

        System.out.println("=================FINISH Transaction SERVICE=================");

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

    public TransferResponse getSavedTransaction(TransferRequest request){
        List<TransactionEntity> transactionList = transactionRepository.findByTransferId(request.getTransferId());

        if(transactionList.size() != 0) {
            System.out.println("Найден Idempotency-Key. Отдаю трансакцию из Базы. Транзакция найдена.");
            TransactionEntity transaction = transactionList.get(0);

            return TransferResponse.builder()
                    .transferId(transaction.getTransferId())
                    .message("Transaction successfully completed")
                    .transactionId(transaction.getTransactionId())
                    .build();
        }
        System.out.println("Найден Idempotency-Key. Отдаю трансакцию из Базы. Транзакция не найдена.");
        return TransferResponse.builder()
                .message("Transaction not completed")
                .transferId(request.getTransferId())
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
