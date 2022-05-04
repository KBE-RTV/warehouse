package com.kbertv.warehouse.model;

import com.opencsv.bean.CsvBindByName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "components")
public class CelestialBody{

    @CsvBindByName
    @Id
    private String id;
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

    public CelestialBody(String id, String name, int amount, float price, CelestialBodyTypes type, int orbital, float radius, float volume, float mass, float gravity, float rotationVelocity, float orbitalVelocity, float surfaceTemperature) {
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

    // ONLY for CSV Import!!
    public CelestialBody(){
        id = "-1";
        name = "dummy";
        amount = -1;
        price = -1.0f;
        type = CelestialBodyTypes.sun;
        radius = -1.0f;
        volume = -1.0f;
        mass = -1.0f;
        gravity = -1.0f;
        rotationVelocity = -1.0f;
        orbitalVelocity = 1.0f;
        surfaceTemperature = -1.0f;
    }

    public CelestialBodyTypes getType() {
        return type;
    }

    public int getOrbital() {
        return orbital;
    }

    public float getRadius() {
        return radius;
    }

    public float getVolume() {
        return volume;
    }

    public float getMass() {
        return mass;
    }

    public float getGravity() {
        return gravity;
    }

    public float getRotationVelocity() {
        return rotationVelocity;
    }

    public float getOrbitalVelocity() {
        return orbitalVelocity;
    }

    public float getSurfaceTemperature() {
        return surfaceTemperature;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount>=0) {
            this.amount = amount;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addAmount(int amount){
        if (amount>0){
            this.amount +=amount;
        }
    }

    public float getPrice() {
        return price;
    }
}