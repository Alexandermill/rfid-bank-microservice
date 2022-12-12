package com.avantesb.rfidbankmicroservice.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RestCashTransferResponse {
    private UUID transferId;
    private String message;
    private String link;
}
