package com.senerk.casestudy.flightplanning.service.validators.data;

import com.senerk.casestudy.flightplanning.entity.Flight;
import com.senerk.casestudy.flightplanning.models.ValidatorException;

public interface FlightValidator extends CommonValidator {
    void canFlightPersist(Flight flight) throws ValidatorException;
}
