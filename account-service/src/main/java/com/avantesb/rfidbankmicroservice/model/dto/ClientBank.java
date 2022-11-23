package com.avantesb.rfidbankmicroservice.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientBank {
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String identificationNumber;
    private List<String> accountNumbers;

}
