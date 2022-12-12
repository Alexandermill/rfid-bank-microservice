package com.avantesb.rfidbankmicroservice.service.message;

import com.avantesb.rfidbankmicroservice.model.dto.response.CashTransferResponse;
import com.avantesb.rfidbankmicroservice.service.CashTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MessageService {

    @Autowired
    CashTransferService transferService;

    @Bean
    Consumer<CashTransferResponse> transactionStatus(){

        return response -> transferService.updateTransfer(response);
    }



}
