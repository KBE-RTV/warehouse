package com.kbertv.warehouse.model;

import com.kbertv.warehouse.converter.TextToCelestialBodyConverter;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Model for an Planetary System
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class PlanetarySystem implements Serializable {

    @CsvBindByName
    @Id
    private UUID id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String owner;

    @CsvBindAndSplitByName(elementType= CelestialBody.class, splitOn = "\\|", converter = TextToCelestialBodyConverter.class, collectionType = ArrayList.class)
    private ArrayList<CelestialBody> celestialBodies = new ArrayList<>();
}