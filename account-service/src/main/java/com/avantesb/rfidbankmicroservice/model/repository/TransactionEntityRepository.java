package com.avantesb.rfidbankmicroservice.model.repository;

import com.avantesb.rfidbankmicroservice.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByTransferId(Long id);
}