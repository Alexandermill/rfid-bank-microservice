package com.avantesb.rfidbankmicroservice.model.repository;

import com.avantesb.rfidbankmicroservice.model.entity.IdempotencyKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdempKeyRepository extends CrudRepository<IdempotencyKey, String> {



}
