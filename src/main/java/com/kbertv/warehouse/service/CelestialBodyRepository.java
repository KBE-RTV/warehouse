package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.CelestialBody;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository to interact with the Mongo DB
 */
@Repository
public interface CelestialBodyRepository extends MongoRepository<CelestialBody, UUID> {
}
