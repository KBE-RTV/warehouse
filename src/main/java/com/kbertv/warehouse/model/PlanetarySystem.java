package com.kbertv.warehouse.model;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class PlanetarySystem {

    @CsvBindByName
    @Id
    private UUID id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String owner;

    @CsvBindAndSplitByName(elementType= UUID.class, splitOn = "\\|")
    private ArrayList<UUID> celestialBodies = new ArrayList<>();
}