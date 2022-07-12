package com.senerk.casestudy.flightplanning.repository.flight;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.entity.Flight;
import com.senerk.casestudy.flightplanning.repository.CommonRepository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightRepositoryCustom extends CommonRepository<Flight> {
    Optional<Flight> getFlightByCode(String code);

    List<Flight> getFlightByAirPortId(String airPortId, Pageable pageable);

    List<Flight> findByNameIsContains(String name, Pageable pageable);

    List<Flight> getFlightsBySourceAirportTargetAirPortAndDate(AirPort sourceAirPort, AirPort targetAirPort, LocalDate date);

    Long getFlightCountBySourceAirportTargetAirPortAndDate(AirPort sourceAirPort, AirPort targetAirPort, LocalDate date);
}
