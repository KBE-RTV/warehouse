package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.PlanetarySystem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanetarySystemRepository extends MongoRepository<PlanetarySystem, Integer> {
}
