package com.senerk.casestudy.flightplanning.converter;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;

public class DbBooleanConverter implements AttributeConverter<Boolean, String> {

    private static final String YES = "Y";
    private static final String NO = "N";

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return null;
        }
        if (Boolean.TRUE.equals(attribute)) {
            return YES;
        } else {
            return NO;
        }
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData)) {
            return null;
        }
        return YES.equalsIgnoreCase(dbData);
    }
}
