package com.kbertv.warehouse.model;

import com.opencsv.bean.CsvBindByName;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "components")
public class CelestialBody implements Serializable {

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
    private String type;
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

    /**
     * Adds an int value to the amount field
     *
     * @param amount int value
     */
    public void addAmount(int amount){
        if (amount>0){
            this.amount +=amount;
        }
    }
}