package com.kbertv.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.model.PlanetarySystem;
import com.kbertv.warehouse.service.IWarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all celestial bodies", description = "Returns all celestial bodies to the warehouse", tags = {"celestial bodies"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CelestialBody.class))))})
    @GetMapping(value = "/celestialBodies", produces = "application/json")
    public ResponseEntity<String> getAllCelestialBodies() {
        return createResponseEntity(warehouseService.getAllCelestialBodies());
    }

    @Operation(summary = "Get all planetary systems", description = "Returns all planetary systems from the warehouse", tags = {"planetary systems"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlanetarySystem.class))))})
    @GetMapping(value = "/planetarySystems", produces = "application/json")
    public ResponseEntity<String> getAllPlanetarySystems() {
        return createResponseEntity(warehouseService.getAllPlanetarySystems());
    }

    @Operation(summary = "Get celestial body by id", description = "Returns single celestial body from the warehouse", tags = {"celestial bodies"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CelestialBody.class)))),
            @ApiResponse(responseCode = "404", description = "celestial body does not exist")})
    @GetMapping(value = "/celestialBodies/{id}", produces = "application/json")
    public ResponseEntity<String> getCelestialBody(@PathVariable("id") String id) {
        Optional<CelestialBody> celestialBody = warehouseService.getCelestialBody(UUID.fromString(id));
        if (celestialBody.isPresent()) {
            return createResponseEntity(celestialBody);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get planetary systems by id", description = "Returns planetary system product from the warehouse", tags = {"planetary systems"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlanetarySystem.class)))),
            @ApiResponse(responseCode = "404", description = "planetary system does not exist")})
    @GetMapping(value = "/planetarySystems/{id}", produces = "application/json")
    public ResponseEntity<String> getPlanetarySystem(@PathVariable("id") String id) {
        Optional<PlanetarySystem> planetarySystem = warehouseService.getPlanetarySystem(UUID.fromString(id));
        if (planetarySystem.isPresent()) {
            return createResponseEntity(planetarySystem);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create a new planetary system", description = "Adds a new planetary system to the warehouse", tags = {"planetary systems"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")})
    @PostMapping(value = "/planetarySystems", produces = "application/json")
    public ResponseEntity<String> savePlanetarySystem(@RequestBody ArrayList<PlanetarySystem> planetarySystems) {
        warehouseService.savePlanetarySystems(planetarySystems);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<String> createResponseEntity(Object object) {
        try {
            String objectAsJson = objectMapper.writeValueAsString(object);
            return new ResponseEntity<>(objectAsJson, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}