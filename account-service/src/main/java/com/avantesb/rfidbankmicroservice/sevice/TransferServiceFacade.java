package com.avantesb.rfidbankmicroservice.sevice;

import org.springframework.stereotype.Service;

import com.avantesb.rfidbankmicroservice.model.constant.TransferType;
import com.avantesb.rfidbankmicroservice.model.request.TransferRequest;
import com.avantesb.rfidbankmicroservice.model.response.TransferResponseUUID;

@Service
public class TransferServiceFacade {

    TransactionService transactionService;
    CardTransferService cardTransferService;

    public TransferServiceFacade(TransactionService transactionService, CardTransferService cardTransferService) {
        this.transactionService = transactionService;
        this.cardTransferService = cardTransferService;
    }

    public TransferResponseUUID initTransaction (TransferType type, TransferRequest request){
        
        if(type == TransferType.FUND){
            transactionService.fundTransfer(request);
        }

        if


    }
        
}
