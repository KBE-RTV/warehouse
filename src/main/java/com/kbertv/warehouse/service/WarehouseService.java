package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.CelestialBody;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService implements IWarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;

    }

    @Override
    public List<CelestialBody> getAllComponents() {
        return warehouseRepository.findAll();
    }

    @Override
    public Optional<CelestialBody> getComponent(String id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public List<CelestialBody> importCSVToCelestialBodyRepo(String path) throws IOException {
        List<CelestialBody> importedCelestialBodies = importCSVToPOJOs(path);
        List<CelestialBody> existingCelestialBodyRepoEntries = retainExistingCelestialBodyRepoEntries(importedCelestialBodies);

        importedCelestialBodies.removeIf(existingCelestialBodyRepoEntries::contains);
        adjustAmountWithCelestialBodyRepoEntries(existingCelestialBodyRepoEntries);
        importedCelestialBodies.addAll(existingCelestialBodyRepoEntries);

        return warehouseRepository.saveAll(importedCelestialBodies);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void importCSVAtStartUp() {
        try {
            importCSVToCelestialBodyRepo("target/classes/components.csv");
            System.out.print("CSV Imported");
        } catch (IOException e) {
            System.err.print("CSV not Found");
        }
    }

    private List<CelestialBody> importCSVToPOJOs(String path) throws IOException {
        return new CsvToBeanBuilder<CelestialBody>(new FileReader(path))
                .withType(CelestialBody.class)
                .build()
                .parse();
    }

    private List<CelestialBody> retainExistingCelestialBodyRepoEntries(List<CelestialBody> initialList){
        return initialList.stream()
                .filter(celestialBody -> warehouseRepository.findById(celestialBody.getId()).isPresent())
                .collect(Collectors.toList());
    }

    private List<CelestialBody> adjustAmountWithCelestialBodyRepoEntries(List <CelestialBody> initialList){
        for (CelestialBody celestialBody: initialList) {
            Optional<CelestialBody> potentialEntry = warehouseRepository.findById(celestialBody.getId());
            potentialEntry.ifPresent(body -> celestialBody.addAmount(body.getAmount()));
        }
        return initialList;
    }
}
