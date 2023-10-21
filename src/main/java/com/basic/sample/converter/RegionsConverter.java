package com.basic.sample.converter;

import com.amazonaws.regions.Regions;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Objects;

@Converter
public class RegionsConverter implements AttributeConverter<Regions, String> {

    @Override
    public String convertToDatabaseColumn(Regions regions) {
        return Arrays.stream(Regions.values())
                     .filter(e -> Objects.equals(e.getName(), regions.getName()))
                     .map(Regions::getName)
                     .findFirst().get();
    }

    @Override
    public Regions convertToEntityAttribute(String regionName) {
        return Arrays.stream(Regions.values())
                     .filter(e -> Objects.equals(e.getName(), regionName))
                     .findFirst().get();
    }
}
