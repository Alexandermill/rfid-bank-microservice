package com.avantesb.rfidbankmicroservice.model.entity;

import com.avantesb.rfidbankmicroservice.model.AtmStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "atm_terminal")
public class AtmEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private UUID id;

    private String adress;
    private String atmNumber;

    @Enumerated(EnumType.STRING)
    private AtmStatus status;

}
