package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.model.PlanetarySystem;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service with provides the functionality of the Warehouse
 */
@Service
@Slf4j
public class WarehouseService implements IWarehouseService {

    private final CelestialBodyRepository celestialBodyRepository;
    private final PlanetarySystemRepository planetarySystemRepository;
    @Value("${csv.import.path.celestialBodies}")
    private String celestialBodyImportPath;
    @Value("${csv.import.path.planetarySystems}")
    private String planetarySystemImportPath;

    /**
     * Instantiates a new Warehouse service.
     */
    public WarehouseService(CelestialBodyRepository celestialBodyRepository, PlanetarySystemRepository planetarySystemRepository) {
        this.celestialBodyRepository = celestialBodyRepository;

        this.planetarySystemRepository = planetarySystemRepository;
    }

    @Override
    public List<CelestialBody> getAllCelestialBodies() {
        return celestialBodyRepository.findAll();
    }

    @Override
    @Cacheable(value = "celestialBodyCache")
    public Optional<CelestialBody> getCelestialBody(UUID id) {
        return celestialBodyRepository.findById(id);
    }

    @Override
    public List<CelestialBody> importCSVToCelestialBodyRepo(String path) throws IOException {
        List<CelestialBody> importedCelestialBodies = importCSVToCelestialBodies(path);
        return celestialBodyRepository.saveAll(adjustAmountWithCelestialBodyRepoEntries(importedCelestialBodies));
    }

    @Override
    public List<PlanetarySystem> getAllPlanetarySystems() {
        return planetarySystemRepository.findAll();
    }

    @Override
    @Cacheable(value = "planetarySystemCache")
    public Optional<PlanetarySystem> getPlanetarySystem(UUID id) {
        return planetarySystemRepository.findById(id);
    }

    @Override
    public List<PlanetarySystem> importCSVToPlanetarySystemRepo(String path) throws IOException {
        List <PlanetarySystem> importedPlanetarySystems = importCSVToPlanetarySystems(path);
        return planetarySystemRepository.saveAll(importedPlanetarySystems);
    }

    @Override
    public ArrayList<PlanetarySystem> savePlanetarySystems(ArrayList<PlanetarySystem> planetarySystems) {
        return (ArrayList<PlanetarySystem>) planetarySystemRepository.saveAll(planetarySystems);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void importCSVAtStartUp() {
        try {
            importCSVToCelestialBodyRepo(celestialBodyImportPath);
            log.info("Celestial body CSV Imported");
        } catch (IOException e) {
            log.error("Celestial body CSV on Path not Found: "+ celestialBodyImportPath);
        }catch (NumberFormatException e){
            log.error("Celestial body could not be parsed: "+e);
        }

        try {
            importCSVToPlanetarySystemRepo(planetarySystemImportPath);
            log.info("Planetary system CSV Imported");
        } catch (IOException e) {
            log.error("Planetary system CSV on Path not Found: "+ celestialBodyImportPath);
        }catch (NumberFormatException e){
            log.error("Planetary system could not be parsed: "+e);
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

    /**
     * Corrects the amount value of Celestial Bodies in a List
     *
     * @param initialList List to correct
     * @return corrected List
     */
    public List<CelestialBody> adjustAmountWithCelestialBodyRepoEntries(List <CelestialBody> initialList){
        for (CelestialBody celestialBody: initialList) {
            Optional<CelestialBody> potentialEntry = celestialBodyRepository.findById(celestialBody.getId());
            potentialEntry.ifPresent(body -> celestialBody.addAmount(body.getAmount()));
        }
        return initialList;
    }
}