package com.avantesb.rfidbankmicroservice.model.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CardTransferResponse {
    private UUID transferId;
    private String message;
    private String transactionId;
}
