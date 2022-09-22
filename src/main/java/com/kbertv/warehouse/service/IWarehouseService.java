package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.model.PlanetarySystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for the Warehouse Service
 */
public interface IWarehouseService {

    List<CelestialBody> getAllCelestialBodies();

    /**
     * @return  Optional with celestial body if UUID could be found or empty if UUID could not be found.
     */
    Optional<CelestialBody> getCelestialBody(UUID id);

    List<PlanetarySystem> getAllPlanetarySystems();

    /**
     * @return  Optional with celestial body if UUID could be found or empty if UUID could not be found.
     */
    Optional<PlanetarySystem> getPlanetarySystem(UUID id);

    ArrayList<PlanetarySystem> savePlanetarySystems(ArrayList<PlanetarySystem> planetarySystem);

    /**
     * @return List of all Imported Items
     * @throws IOException If the Path doesn't exist.
     */
    List<PlanetarySystem> importCSVToPlanetarySystemRepo(String path) throws IOException;

    /**
     * @return List of all Imported Items
     * @throws IOException If the Path doesn't exist.
     */
    List<CelestialBody> importCSVToCelestialBodyRepo(String path) throws IOException;

    /**
     * Calls {@link #importCSVToCelestialBodyRepo(String) importCSVToCelestialBodyRepo} and {@link #importCSVToPlanetarySystemRepo(String) importCSVToPlanetarySystemRepo}
     */
    void importCSVAtStartUp();
}
