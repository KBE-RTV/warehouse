package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.Component;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends MongoRepository<Component, String> {
}
