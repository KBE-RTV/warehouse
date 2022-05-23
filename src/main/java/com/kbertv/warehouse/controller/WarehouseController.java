package com.kbertv.warehouse.controller;

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
        return createResponseEntity(warehouseService.getAllComponents());
    }

    @GetMapping(value = "/products", produces = "application/json")
    public ResponseEntity<String> getAllProducts(){
        return createResponseEntity(warehouseService.getAllProducts());
    }

    @GetMapping(value = "/components/{id}", produces = "application/json")
    public ResponseEntity<String> getComponent(@PathVariable("id") String id){
        Optional<CelestialBody> component = warehouseService.getComponent(UUID.fromString(id));
        if(component.isPresent()){
            return createResponseEntity(component);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<String> getProduct(@PathVariable("id") String id){
        Optional<PlanetarySystem> product = warehouseService.getProduct(UUID.fromString(id));
        if(product.isPresent()){
            return createResponseEntity(product);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<String> saveProduct(@RequestBody PlanetarySystem planetarySystem, @PathVariable("id") String id){
        if (planetarySystem.getId().toString().equals(id)) {
            PlanetarySystem product = warehouseService.saveProduct(planetarySystem);
            return createResponseEntity(product);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<String> createResponseEntity(Object object){
        try {
            String productJsonFormat = objectMapper.writeValueAsString(object);
            return new ResponseEntity<>(productJsonFormat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}