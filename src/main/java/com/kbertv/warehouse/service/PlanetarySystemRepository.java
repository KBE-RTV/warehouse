package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.PlanetarySystem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlanetarySystemRepository extends MongoRepository<PlanetarySystem, UUID> {
}
