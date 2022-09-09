package com.kbertv.warehouse.converter;

import com.kbertv.warehouse.model.CelestialBody;
import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.util.UUID;

public class TextToCelestialBodyConverter extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String value) {
        String[] split = value.split("&", 0);
        CelestialBody celestialBody = new CelestialBody();
        celestialBody.setId(UUID.fromString(split[0]));
        celestialBody.setName(split[1]);
        celestialBody.setAmount(Integer.parseInt(split[2]));
        celestialBody.setPrice(Float.parseFloat(split[3]));
        celestialBody.setType(split[4]);
        celestialBody.setRadius(Float.parseFloat(split[5]));
        celestialBody.setVolume(Float.parseFloat(split[6]));
        celestialBody.setMass(Float.parseFloat(split[7]));
        celestialBody.setGravity(Float.parseFloat(split[8]));
        celestialBody.setRotationVelocity(Float.parseFloat(split[9]));
        celestialBody.setOrbitalVelocity(Float.parseFloat(split[10]));
        celestialBody.setSurfaceTemperature(Float.parseFloat(split[11]));
        return celestialBody;
    }
}
