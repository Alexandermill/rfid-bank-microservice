package com.avantesb.rfidbankmicroservice.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestTransferResponse {
    private String transferId;
    private String message;
    private String link;
}
