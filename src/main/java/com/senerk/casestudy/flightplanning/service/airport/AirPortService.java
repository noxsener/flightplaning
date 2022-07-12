package com.senerk.casestudy.flightplanning.service.airport;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.models.ValidatorException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirPortService {

    /**
     * Returns all AirPorts in database
     *
     * @return AirPort Data Collection
     */
    List<AirPort> getAirports();

    /**
     * Returns all AirPorts in database
     *
     * @param pageable Paging info
     * @return AirPort Data
     */
    List<AirPort> getAirports(Pageable pageable);

    /**
     * Returns all AirPorts in database
     *
     * @param code Airport Code
     * @return AirPort Data
     */
    AirPort getAirportByCode(String code);

    /**
     * Returns all AirPorts in database
     *
     * @param displayName Text containing the searched name
     * @return AirPort Data Collection
     */
    List<AirPort> getAirportByNameContains(String displayName);

    /**
     * Saves a new airport data
     *
     * @param airPort New Airport data
     * @return New Airport data id
     */
    String save(AirPort airPort);

    /**
     * Updates a new airport data
     *
     * @param airPort New Airport data
     * @return Nothing
     */
    void update(AirPort airPort) throws ValidatorException;

    /**
     * Deletes Airport data of given id
     *
     * @param airPortId Id of Airport Data
     * @return Nothing
     */
    void delete(String airPortId) throws ValidatorException;
}
