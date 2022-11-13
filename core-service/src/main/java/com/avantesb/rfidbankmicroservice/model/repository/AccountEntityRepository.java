package com.avantesb.rfidbankmicroservice.model.repository;

import com.avantesb.rfidbankmicroservice.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByNumber(String number);
}