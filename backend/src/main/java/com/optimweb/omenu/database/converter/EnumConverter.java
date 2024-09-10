package com.optimweb.omenu.database.converter;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EnumConverter<E extends Enum<E>> implements AttributeConverter<List<E>, String> {

    private final Class<E> enumType;

    public EnumConverter(Class<E> enumType) {
        this.enumType = enumType;
    }

    @Override
    public String convertToDatabaseColumn(List<E> enums) {
        if (enums == null || enums.isEmpty()) {
            return null;
        }
        return enums.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<E> convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(s.split(","))
                .map(name -> Enum.valueOf(enumType, name.trim()))
                .toList();
    }
}
