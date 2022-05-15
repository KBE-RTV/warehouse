package com.kbertv.warehouse.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "components")
public class CelestialBody{

    @CsvBindByName
    @Id
    private int id;
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

    public CelestialBody(int id, String name, int amount, float price, CelestialBodyTypes type, int orbital, float radius, float volume, float mass, float gravity, float rotationVelocity, float orbitalVelocity, float surfaceTemperature) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.type = type;
        this.orbital = orbital;
        this.radius = radius;
        this.volume = volume;
        this.mass = mass;
        this.gravity = gravity;
        this.rotationVelocity = rotationVelocity;
        this.orbitalVelocity = orbitalVelocity;
        this.surfaceTemperature = surfaceTemperature;
    }

    public void addAmount(int amount){
        if (amount>0){
            this.amount +=amount;
        }
    }
}