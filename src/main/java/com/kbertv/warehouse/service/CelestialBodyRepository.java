package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.CelestialBody;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CelestialBodyRepository extends MongoRepository<CelestialBody, Integer> {
}
