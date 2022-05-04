package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.CelestialBody;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IWarehouseService {
    List<CelestialBody> getAllComponents();

    Optional<CelestialBody> getComponent(String id);

    List<CelestialBody> importCSVToCelestialBodyRepo(String path) throws IOException;
}
