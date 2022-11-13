package com.avantesb.rfidbankmicroservice.model.repository;

import com.avantesb.rfidbankmicroservice.model.entity.ClientBankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientBankEntityRepository extends JpaRepository<ClientBankEntity, Long> {

    Optional<ClientBankEntity> findByIdentificationNumber(String number);


}