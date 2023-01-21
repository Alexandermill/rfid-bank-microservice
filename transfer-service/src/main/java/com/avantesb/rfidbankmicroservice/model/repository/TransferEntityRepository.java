package com.avantesb.rfidbankmicroservice.model.repository;

import com.avantesb.rfidbankmicroservice.model.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferEntityRepository extends JpaRepository<TransferEntity, Long> {

    Optional<TransferEntity> findByTransferId(String transferId);
}