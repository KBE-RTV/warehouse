package com.kbertv.warehouse.model;

import com.kbertv.warehouse.config.TextToCelestialBody;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class PlanetarySystem {

    @CsvBindByName
    @Id
    private UUID id;

    @CsvBindByName
    private String name;

    @CsvBindAndSplitByName(elementType= CelestialBody.class, splitOn = "\\|", converter = TextToCelestialBody.class)
    private ArrayList<CelestialBody> celestialBodies = new ArrayList<>();
}