package com.avantesb.rfidbankmicroservice.sevice.message;


import com.avantesb.rfidbankmicroservice.model.dto.AccountBank;
import com.avantesb.rfidbankmicroservice.model.request.TransferRequest;
import com.avantesb.rfidbankmicroservice.model.response.TransferResponse;
import com.avantesb.rfidbankmicroservice.sevice.AccountService;
import com.avantesb.rfidbankmicroservice.sevice.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class MessageService {

    private AccountService accountService;
    private TransactionService transactionService;

    @Bean
    Function<List<Long>, List<AccountBank>> getAccounts(){
        return input -> accountService.getAccountsByClientId(input);
    }


    @Bean
    Function<TransferRequest, TransferResponse> commitTransaction(){
        return request -> transactionService.fundTransfer(request);
    }
}
