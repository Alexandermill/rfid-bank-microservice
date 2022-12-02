package com.avantesb.rfidbankmicroservice.model.repository;

import com.avantesb.rfidbankmicroservice.model.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferEntityRepository extends JpaRepository<TransferEntity, Long> {
}