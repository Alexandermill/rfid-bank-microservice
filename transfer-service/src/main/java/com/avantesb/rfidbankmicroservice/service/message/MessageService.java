package com.avantesb.rfidbankmicroservice.service.message;

import com.avantesb.rfidbankmicroservice.model.dto.response.TransferResponse;
import com.avantesb.rfidbankmicroservice.service.FundTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MessageService {

    @Autowired
    FundTransferService transferService;

    @Bean
    Consumer<TransferResponse> transactionStatus(){

        return response -> transferService.updateTransfer(response);
    }



}
