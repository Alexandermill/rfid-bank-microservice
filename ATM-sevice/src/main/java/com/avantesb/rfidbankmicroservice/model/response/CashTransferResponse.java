package com.avantesb.rfidbankmicroservice.model.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CashTransferResponse {
    private UUID transferId;
    private String message;
    private String transactionId;
}
