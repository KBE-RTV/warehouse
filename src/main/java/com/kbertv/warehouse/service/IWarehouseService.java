package com.kbertv.warehouse.service;

import com.kbertv.warehouse.model.Component;

import java.util.List;
import java.util.Optional;

public interface IWarehouseService {
    List<Component> getAllComponents();

    Optional<Component> getComponent(String id);
}
