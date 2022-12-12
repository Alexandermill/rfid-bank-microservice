package com.avantesb.rfidbankmicroservice.model.repo;

import com.avantesb.rfidbankmicroservice.model.entity.CardEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CardRepository extends CrudRepository<CardEntity, Long> {

    Optional<CardEntity> findByCardNumber(String number);

    boolean existsCardEntityByCardNumber(String cardNumber);
}
