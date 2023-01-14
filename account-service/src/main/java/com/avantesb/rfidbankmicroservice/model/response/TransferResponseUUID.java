package com.avantesb.rfidbankmicroservice.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TransferResponseUUID {
    private UUID transferId;
    private String message;
    private String transactionId;
}
