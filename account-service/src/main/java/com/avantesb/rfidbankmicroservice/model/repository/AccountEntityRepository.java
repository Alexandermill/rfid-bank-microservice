package com.avantesb.rfidbankmicroservice.model.repository;

import com.avantesb.rfidbankmicroservice.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByNumber(String number);

    List<AccountEntity> findByClientId(Long clientId);
}