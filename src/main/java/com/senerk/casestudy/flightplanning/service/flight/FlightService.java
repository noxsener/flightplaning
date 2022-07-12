package com.senerk.casestudy.flightplanning.service.flight;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.entity.Flight;
import com.senerk.casestudy.flightplanning.models.ValidatorException;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    Flight getFlightByCode(String code);

    List<Flight> getFlightByAirPortId(String airPortId, Pageable pageable) throws ValidatorException;

    List<Flight> getFlightByNameContains(String name, Pageable pageable);

    List<Flight> getFlightsByAirportAndDate(AirPort sourceAirPort, AirPort targetAirPort, LocalDate date);

    Long getFlightCountBySourceAirportTargetAirPortAndDate(AirPort sourceAirPort, AirPort targetAirPort, LocalDate date);

    String save(Flight flight) throws ValidatorException;

    void update(Flight flight) throws ValidatorException;

    void delete(String flightId) throws ValidatorException;
}
