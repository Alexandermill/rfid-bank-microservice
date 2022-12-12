package com.avantesb.rfidbankmicroservice.model.repo;

import com.avantesb.rfidbankmicroservice.model.entity.CashTransferEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CashTransferRepository extends CrudRepository<CashTransferEntity, UUID> {
}
