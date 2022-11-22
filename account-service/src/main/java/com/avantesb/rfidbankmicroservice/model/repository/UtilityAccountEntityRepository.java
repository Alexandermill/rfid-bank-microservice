package com.avantesb.rfidbankmicroservice.model.repository;

import com.avantesb.rfidbankmicroservice.model.entity.UtilityAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilityAccountEntityRepository extends JpaRepository<UtilityAccountEntity, Long> {

    Optional<UtilityAccountEntity> findByProviderName(String name);
}