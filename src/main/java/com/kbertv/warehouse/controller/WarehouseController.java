package com.kbertv.warehouse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping(value = "/components", produces = "application/json")
    public ResponseEntity<String> getAllComponents(){
        String componentJsonFormat;
        try {
            componentJsonFormat = objectMapper.writeValueAsString(warehouseService.getAllComponents());
            return new ResponseEntity<>(componentJsonFormat, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/components/{id}", produces = "application/json")
    public ResponseEntity<String> getComponent(@PathVariable("id") String id){
        String componentJsonFormat;
        Optional<CelestialBody> component = warehouseService.getComponent(id);

        if (component.isPresent()){
            try {
                componentJsonFormat = objectMapper.writeValueAsString(component.get());
                return new ResponseEntity<>(componentJsonFormat, HttpStatus.OK);
            } catch (JsonProcessingException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
