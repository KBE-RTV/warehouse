package com.kbertv.warehouse.controller;

import com.kbertv.warehouse.service.WarehouseService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }
}
