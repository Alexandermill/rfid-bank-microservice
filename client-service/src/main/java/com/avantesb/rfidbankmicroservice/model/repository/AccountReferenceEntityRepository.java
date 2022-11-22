package com.avantesb.rfidbankmicroservice.model.repository;

import com.avantesb.rfidbankmicroservice.model.entity.AccountReferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountReferenceEntityRepository extends JpaRepository<AccountReferenceEntity, Long> {
}