package com.avantesb.rfidbankmicroservice.model.repo;

import com.avantesb.rfidbankmicroservice.model.entity.AtmEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AtmRepository extends CrudRepository<UUID, AtmEntity> {
}
