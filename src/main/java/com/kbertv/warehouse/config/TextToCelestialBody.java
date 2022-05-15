package com.kbertv.warehouse.config;

import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.model.CelestialBodyTypes;
import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class TextToCelestialBody extends AbstractCsvConverter {

    @Override
    public Object convertToRead(String s) throws CsvDataTypeMismatchException {
        CelestialBody celestialBody = new CelestialBody();
        String[] split = s.split("\\+");
        if (split.length!=13) {
            throw new CsvDataTypeMismatchException();
        }
        try{
        celestialBody.setId(Integer.parseInt(split[0]));
        celestialBody.setName(split[1]);
        celestialBody.setAmount(Integer.parseInt(split[2]));
        celestialBody.setPrice(Float.parseFloat(split[3]));
        celestialBody.setType(CelestialBodyTypes.valueOf(split[4]));
        celestialBody.setOrbital(Integer.parseInt(split[5]));
        celestialBody.setRadius(Float.parseFloat(split[6]));
        celestialBody.setVolume(Float.parseFloat(split[7]));
        celestialBody.setMass(Float.parseFloat(split[8]));
        celestialBody.setGravity(Float.parseFloat(split[9]));
        celestialBody.setRotationVelocity(Float.parseFloat(split[10]));
        celestialBody.setOrbitalVelocity(Float.parseFloat(split[11]));
        celestialBody.setSurfaceTemperature(Float.parseFloat(split[12]));
        }catch (NumberFormatException e){
            throw new CsvDataTypeMismatchException(e.getMessage());
        }

        return celestialBody;
    }

    @Override
    public String convertToWrite(Object value) {
        CelestialBody celestialBody = (CelestialBody) value;
        return String.format("%s+%s+%s+%s+%s+%s+%s+%s+%s+%s+%s+%s+%s",
                celestialBody.getId(),
                celestialBody.getName(),
                celestialBody.getAmount(),
                celestialBody.getPrice(),
                celestialBody.getType(),
                celestialBody.getOrbital(),
                celestialBody.getRadius(),
                celestialBody.getVolume(),
                celestialBody.getMass(),
                celestialBody.getGravity(),
                celestialBody.getRotationVelocity(),
                celestialBody.getOrbitalVelocity(),
                celestialBody.getSurfaceTemperature());
    }


}
