package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.model.PlanetarySystem;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IWarehouseService {
    List<CelestialBody> getAllComponents();

    Optional<CelestialBody> getComponent(int id);

    List<CelestialBody> importCSVToCelestialBodyRepo(String path) throws IOException;

    List<PlanetarySystem> getAllProducts();

    Optional<PlanetarySystem> getProduct(int id);

    List<PlanetarySystem> importCSVToPlanetarySystemRepo(String path) throws IOException;
}
