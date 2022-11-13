package com.avantesb.rfidbankmicroservice.model.dto.response;

import lombok.*;

@Data
public class FundTransferResponse {
    private String message;
    private String transactionId;
}
