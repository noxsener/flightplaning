package com.senerk.casestudy.flightplanning.service.flight;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.entity.Flight;
import com.senerk.casestudy.flightplanning.models.ValidatorException;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    /**
     * Return flight info
     * @param code flight's code
     * @return Flight info
     */
    Flight getFlightByCode(String code);

    /**
     * Flight list of airport with pagination
     * @param airPortId Airport Id
     * @param pageable Paging info
     * @return Flights Collection
     */
    List<Flight> getFlightByAirPortId(String airPortId, Pageable pageable) throws ValidatorException;

    /**
     * Return flights collection by name with pagination
     * @param name Flight name contains
     * @param pageable Paging info
     * @return Flights collection
     */
    List<Flight> getFlightByNameContains(String name, Pageable pageable);

    /**
     * Get Flight list from an airport to another in a day
     * @param sourceAirPort Takeoff airport
     * @param targetAirPort Landing airport
     * @param date TakeoffDay (morning-to night)
     * @return Flight collection
     */
    List<Flight> getFlightsByAirportAndDate(AirPort sourceAirPort, AirPort targetAirPort, LocalDate date);

    /**
     * Get Flight count from an airport to another in a day
     * @param sourceAirPort Takeoff airport
     * @param targetAirPort Landing airport
     * @param date TakeoffDay (morning-to night)
     * @return Flight collection
     */
    Long getFlightCountBySourceAirportTargetAirPortAndDate(AirPort sourceAirPort, AirPort targetAirPort, LocalDate date);

    /**
     * Save a new flight record
     * @param flight New Data Flight info for save
     * @return Saved data id
     */
    String save(Flight flight) throws ValidatorException;

    /**
     * Update a flight info that is already in db
     * @param flight New Data Flight info for update
     * @return Nothing
     */
    void update(Flight flight) throws ValidatorException;

    /**
     * Delete a flight from db (set passive)
     * @param flightId flight id
     * @return nothing
     */
    void delete(String flightId) throws ValidatorException;
}
