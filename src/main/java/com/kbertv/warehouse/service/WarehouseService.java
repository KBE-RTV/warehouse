package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.model.PlanetarySystem;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService implements IWarehouseService {

    private final CelestialBodyRepository celestialBodyRepository;
    private final PlanetarySystemRepository planetarySystemRepository;

    public WarehouseService(CelestialBodyRepository celestialBodyRepository, PlanetarySystemRepository planetarySystemRepository) {
        this.celestialBodyRepository = celestialBodyRepository;

        this.planetarySystemRepository = planetarySystemRepository;
    }

    @Override
    public List<CelestialBody> getAllComponents() {
        return celestialBodyRepository.findAll();
    }

    @Override
    public Optional<CelestialBody> getComponent(int id) {
        return celestialBodyRepository.findById(id);
    }

    @Override
    public List<CelestialBody> importCSVToCelestialBodyRepo(String path) throws IOException {
        List<CelestialBody> importedCelestialBodies = importCSVToCelestialBodies(path);
        return celestialBodyRepository.saveAll(adjustAmountWithCelestialBodyRepoEntries(importedCelestialBodies));
    }

    @Override
    public List<PlanetarySystem> getAllProducts() {
        return planetarySystemRepository.findAll();
    }

    @Override
    public Optional<PlanetarySystem> getProduct(int id) {
        return planetarySystemRepository.findById(id);
    }

    @Override
    public List<PlanetarySystem> importCSVToPlanetarySystemRepo(String path) throws IOException {
        List <PlanetarySystem> importedPlanetarySystems = importCSVToPlanetarySystems(path);
        return planetarySystemRepository.saveAll(importedPlanetarySystems);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void importCSVAtStartUp() {
        try {
            importCSVToCelestialBodyRepo("target/classes/components.csv");
            System.out.println("Component CSV Imported");
        } catch (IOException e) {
            System.err.println("Component CSV not Found");
        }

        try {
            importCSVToPlanetarySystemRepo("target/classes/products.csv");
            System.out.println("Product CSV Imported");
        } catch (IOException e) {
            System.err.println("Product CSV not Found");
        }
    }

    private List<CelestialBody> importCSVToCelestialBodies(String path) throws IOException {
        return new CsvToBeanBuilder<CelestialBody>(new FileReader(path))
                .withType(CelestialBody.class)
                .build()
                .parse();
    }

    private List<PlanetarySystem> importCSVToPlanetarySystems(String path) throws IOException {
        return new CsvToBeanBuilder<PlanetarySystem>(new FileReader(path))
                .withType(PlanetarySystem.class)
                .build()
                .parse();
    }

    public List<CelestialBody> adjustAmountWithCelestialBodyRepoEntries(List <CelestialBody> initialList){
        for (CelestialBody celestialBody: initialList) {
            Optional<CelestialBody> potentialEntry = celestialBodyRepository.findById(celestialBody.getId());
            potentialEntry.ifPresent(body -> celestialBody.addAmount(body.getAmount()));
        }
        return initialList;
    }
}