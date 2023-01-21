package com.avantesb.rfidbankmicroservice.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TransferResponse {
    private String transferId;
    private String message;
    private String transactionId;
}
