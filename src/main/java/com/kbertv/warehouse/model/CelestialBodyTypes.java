package com.kbertv.warehouse.model;

public enum CelestialBodyTypes {

    gasGiant("Gas Giant"),
    iceGiant("Ice Giant"),
    meteor("Meteor"),
    asteroid("Asteroid"),
    moon("Moon"),
    dwarfPlanet("Dwarf Planet"),
    planet("Planet");

    private String type;

    CelestialBodyTypes(String type) {
        this.type = type;
    }
}
