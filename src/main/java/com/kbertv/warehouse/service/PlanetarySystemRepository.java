package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.PlanetarySystem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PlanetarySystemRepository extends MongoRepository<PlanetarySystem, UUID> {
}
