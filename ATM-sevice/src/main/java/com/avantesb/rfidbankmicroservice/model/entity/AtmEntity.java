package com.avantesb.rfidbankmicroservice.model.entity;

import com.avantesb.rfidbankmicroservice.model.AtmStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AtmEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private UUID id;

    private String adress;
    private String atmNumber;

    @Enumerated(EnumType.STRING)
    private AtmStatus status;

}
