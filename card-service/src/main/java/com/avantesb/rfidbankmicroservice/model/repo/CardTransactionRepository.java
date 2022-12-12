package com.avantesb.rfidbankmicroservice.model.repo;

import com.avantesb.rfidbankmicroservice.model.entity.CardTransfer;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CardTransactionRepository extends CrudRepository<CardTransfer, UUID> {
}
