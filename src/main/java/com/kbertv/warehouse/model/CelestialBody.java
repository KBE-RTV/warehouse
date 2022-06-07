package com.kbertv.warehouse.model;

import com.opencsv.bean.CsvBindByName;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "components")
public class CelestialBody{

    @CsvBindByName
    @Id
    private UUID id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private int amount;
    @CsvBindByName
    private float price;
    @CsvBindByName
    private CelestialBodyTypes type;
    @CsvBindByName
    private int orbital;
    @CsvBindByName
    private float radius;
    @CsvBindByName
    private float volume;
    @CsvBindByName
    private float mass;
    @CsvBindByName
    private float gravity;
    @CsvBindByName
    private float rotationVelocity;
    @CsvBindByName
    private float orbitalVelocity;
    @CsvBindByName
    private float surfaceTemperature;

    public void addAmount(int amount){
        if (amount>0){
            this.amount +=amount;
        }
    }
}