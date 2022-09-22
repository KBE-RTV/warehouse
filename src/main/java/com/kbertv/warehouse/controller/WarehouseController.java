package com.kbertv.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.model.PlanetarySystem;
import com.kbertv.warehouse.service.IWarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class WarehouseController {

    private final IWarehouseService warehouseService;
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public WarehouseController(IWarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping(value = "/celestialBodies", produces = "application/json")
    public ResponseEntity<String> getAllCelestialBodies(){
        return createResponseEntity(warehouseService.getAllCelestialBodies());
    }

    @GetMapping(value = "/planetarySystems", produces = "application/json")
    public ResponseEntity<String> getAllPlanetarySystems(){
        return createResponseEntity(warehouseService.getAllPlanetarySystems());
    }

    @GetMapping(value = "/celestialBodies/{id}", produces = "application/json")
    public ResponseEntity<String> getCelestialBody(@PathVariable("id") String id){
        Optional<CelestialBody> celestialBody = warehouseService.getCelestialBody(UUID.fromString(id));
        if(celestialBody.isPresent()){
            return createResponseEntity(celestialBody);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/planetarySystems/{id}", produces = "application/json")
    public ResponseEntity<String> getPlanetarySystem(@PathVariable("id") String id){
        Optional<PlanetarySystem> planetarySystem = warehouseService.getPlanetarySystem(UUID.fromString(id));
        if(planetarySystem.isPresent()){
            return createResponseEntity(planetarySystem);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/planetarySystems", produces = "application/json")
    public ResponseEntity<String> savePlanetarySystem(@RequestBody ArrayList<PlanetarySystem> planetarySystems){
        warehouseService.savePlanetarySystems(planetarySystems);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<String> createResponseEntity(Object object){
        try {
            String objectAsJson = objectMapper.writeValueAsString(object);
            return new ResponseEntity<>(objectAsJson, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}