package com.avantesb.rfidbankmicroservice.sevice;

import com.avantesb.rfidbankmicroservice.model.constant.TransferType;
import com.avantesb.rfidbankmicroservice.model.request.TransferRequest;
import com.avantesb.rfidbankmicroservice.model.response.TransferResponse;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceFacade {

    TransactionService transactionService;
    CardTransferService cardTransferService;

    public TransferServiceFacade(TransactionService transactionService, CardTransferService cardTransferService) {
        this.transactionService = transactionService;
        this.cardTransferService = cardTransferService;
    }

    public TransferResponse initTransactionByType (TransferType type, TransferRequest request){
        
        if(type == TransferType.FUND){
            transactionService.fundTransfer(request);
        }


        return null;
    }
        
}
