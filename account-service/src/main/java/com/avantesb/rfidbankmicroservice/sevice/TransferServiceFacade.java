package com.avantesb.rfidbankmicroservice.sevice;

import com.avantesb.rfidbankmicroservice.model.constant.TransferType;
import com.avantesb.rfidbankmicroservice.model.request.TransferRequest;
import com.avantesb.rfidbankmicroservice.model.response.TransferResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransferServiceFacade {

    private final TransactionService transactionService;
    private final CardTransferService cardTransferService;


    public TransferResponse initTransactionByType (TransferType type, TransferRequest request){
        
        if(type == TransferType.FUND){
            return transactionService.fundTransfer(request);
        }

        if (type == TransferType.CARD){
            return cardTransferService.cardTransfer(request);
        }

        return TransferResponse.builder()
                .message("Not found Type Transfer")
                .build();
    }
        
}
