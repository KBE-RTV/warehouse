package com.kbertv.warehouse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.model.PlanetarySystem;
import com.kbertv.warehouse.service.IWarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class WarehouseController {

    private final IWarehouseService warehouseService;
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public WarehouseController(IWarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }
    @GetMapping(value = "/components", produces = "application/json")
    public ResponseEntity<String> getAllComponents(){
        String componentJsonFormat;
        try {
            componentJsonFormat = objectMapper.writeValueAsString(warehouseService.getAllComponents());
            return new ResponseEntity<>(componentJsonFormat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/products", produces = "application/json")
    public ResponseEntity<String> getAllProducts(){
        String productJsonFormat;
        try {
            productJsonFormat = objectMapper.writeValueAsString(warehouseService.getAllProducts());
            return new ResponseEntity<>(productJsonFormat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/components/{id}", produces = "application/json")
    public ResponseEntity<String> getComponent(@PathVariable("id") String id){
        String componentJsonFormat;
        Optional<CelestialBody> component = warehouseService.getComponent(UUID.fromString(id));

        if (component.isPresent()){
            try {
                componentJsonFormat = objectMapper.writeValueAsString(component.get());
                return new ResponseEntity<>(componentJsonFormat, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<String> getProduct(@PathVariable("id") String id){
        String productJsonFormat;
        Optional<PlanetarySystem> product = warehouseService.getProduct(UUID.fromString(id));

        if (product.isPresent()){
            try {
                productJsonFormat = objectMapper.writeValueAsString(product.get());
                return new ResponseEntity<>(productJsonFormat, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<String> saveProduct(@RequestBody PlanetarySystem planetarySystem, @PathVariable("id") String id){
        String productJsonFormat;
        if (planetarySystem.getId().toString().equals(id)) {
            PlanetarySystem product = warehouseService.saveProduct(planetarySystem);
            try {
                productJsonFormat = objectMapper.writeValueAsString(product);
                return new ResponseEntity<>(productJsonFormat, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}