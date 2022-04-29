package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService implements IWarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;

    }

    @Override
    public List<Component> getAllComponents() {
        return warehouseRepository.findAll();
    }

    @Override
    public Optional<Component> getComponent(String id) {
        return warehouseRepository.findById(id);
    }
}
