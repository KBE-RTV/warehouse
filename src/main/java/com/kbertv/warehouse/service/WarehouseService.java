package com.kbertv.warehouse.service;

import org.springframework.stereotype.Service;

@Service
public class WarehouseService implements IWarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;

    }
}
