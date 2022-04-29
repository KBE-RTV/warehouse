package com.kbertv.warehouse.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "warehouse")
public class CelestialBody extends Component{

    private final CelestialBodyTypes type;
    private final int orbital;
    private final float radius;
    private final float volume;
    private final float mass;
    private final float gravity;
    private final float rotationVelocity;
    private final float orbitalVelocity;
    private final float surfaceTemperature;

    public CelestialBody(String id, String name, int amount, CelestialBodyTypes type, int orbital, float radius, float volume, float mass, float gravity, float rotationSpeed, float orbitalVelocity, float surfaceTemperature) {
        super(id, name, amount);
        this.type = type;
        this.orbital = orbital;
        this.radius = radius;
        this.volume = volume;
        this.mass = mass;
        this.gravity = gravity;
        this.rotationVelocity = rotationSpeed;
        this.orbitalVelocity = orbitalVelocity;
        this.surfaceTemperature = surfaceTemperature;
    }
}