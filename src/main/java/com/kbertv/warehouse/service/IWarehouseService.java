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
    /**
     * Gets all Components form the DB
     *
     * @return List of all Components
     */
    List<CelestialBody> getAllComponents();

    /**
     * Gets a specific Component from the DB
     *
     * @param id UUID of the Component
     * @return  Optional with Component if UUID could be found.
     *          Empty Optional if UUID could not be found.
     */
    Optional<CelestialBody> getComponent(UUID id);

    /**
     * Imports a Component CSV into the DB
     *
     * @param path Path to the CSV
     * @return List of all Imported Items
     * @throws IOException If the Path doesn't exist.
     */
    List<CelestialBody> importCSVToCelestialBodyRepo(String path) throws IOException;

    /**
     * Gets all Products form the DB
     *
     * @return List of all Products
     */
    List<PlanetarySystem> getAllProducts();

    /**
     * Gets a specific Product from the DB
     *
     * @param id UUID of the Product
     * @return  Optional with Product if UUID could be found.
     *          Empty Optional if UUID could not be found.
     */
    Optional<PlanetarySystem> getProduct(UUID id);

    /**
     * Imports a Product CSV into the DB
     *
     * @param path Path to the CSV
     * @return List of all Imported Items
     * @throws IOException If the Path doesn't exist.
     */
    List<PlanetarySystem> importCSVToPlanetarySystemRepo(String path) throws IOException;

    /**
     * Save Products to the DB
     *
     * @param planetarySystem Product to be saved
     * @return all saved Products
     */
    ArrayList<PlanetarySystem> saveProducts(ArrayList<PlanetarySystem> planetarySystem);

    /**
     * Import csv at start up.
     */
    void importCSVAtStartUp();
}
