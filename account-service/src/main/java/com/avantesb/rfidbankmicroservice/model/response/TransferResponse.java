package com.avantesb.rfidbankmicroservice.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferResponse {
    private String transferId;
    private String message;
    private String transactionId;
}
