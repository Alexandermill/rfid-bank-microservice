package com.avantesb.rfidbankmicroservice.model.dto;

import com.avantesb.rfidbankmicroservice.model.AtmStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
public class AtmDTO {


    private UUID id;
    private String adress;
    private String atmNumber;
    private AtmStatus status;

}
