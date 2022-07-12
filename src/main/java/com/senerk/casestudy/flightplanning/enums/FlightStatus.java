package com.senerk.casestudy.flightplanning.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum FlightStatus {
    IN_PLANNING,
    PLANNED_PENDING_APPROVAL,
    PLANNED_APPROVED,
    CANCELLED;

    public static FlightStatus getValue(final String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (FlightStatus flightStatusIndex : FlightStatus.values()) {
            if (flightStatusIndex.name().equalsIgnoreCase(name)) {
                return flightStatusIndex;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
