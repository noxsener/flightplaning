package com.senerk.casestudy.flightplanning.service.flight.impl;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.entity.Flight;
import com.senerk.casestudy.flightplanning.models.ValidatorException;
import com.senerk.casestudy.flightplanning.repository.flight.FlightRepository;
import com.senerk.casestudy.flightplanning.service.flight.FlightService;
import com.senerk.casestudy.flightplanning.service.validators.data.FlightValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightValidator flightValidator;

    public FlightServiceImpl(FlightRepository flightRepository, FlightValidator flightValidator) {
        this.flightRepository = flightRepository;
        this.flightValidator = flightValidator;
    }

    @Override
    public Flight getFlightByCode(String code) {
        return flightRepository.getFlightByCode(code).orElse(null);
    }

    @Override
    public List<Flight> getFlightByAirPortId(String airPortId, Pageable pageable) throws ValidatorException {
        flightValidator.validId(airPortId);
        return flightRepository.getFlightByAirPortId(airPortId, pageable);
    }

    @Override
    public List<Flight> getFlightByNameContains(String name, Pageable pageable) {
        return flightRepository.findByNameIsContains(name, pageable);
    }

    @Override
    public List<Flight> getFlightsByAirportAndDate(AirPort sourceAirPort, AirPort targetAirPort, LocalDate date) {
        return flightRepository.getFlightsBySourceAirportTargetAirPortAndDate(sourceAirPort, targetAirPort, date);
    }

    @Override
    public Long getFlightCountBySourceAirportTargetAirPortAndDate(AirPort sourceAirPort, AirPort targetAirPort, LocalDate date) {
        return flightRepository.getFlightCountBySourceAirportTargetAirPortAndDate(sourceAirPort, targetAirPort, date);
    }

    @Override
    public String save(Flight flight) throws ValidatorException {
        flightValidator.canFlightPersist(flight);
        Flight flightSaved = flightRepository.save(flight);
        flightRepository.detach(flightSaved);
        return flightSaved.getId();
    }

    @Override
    public void update(Flight flight) throws ValidatorException {
        flightValidator.validId(flight);
        flightValidator.canFlightPersist(flight);
        Optional<Flight> flightOptional = flightRepository.findById(flight.getId());
        flightValidator.isDataActive(flightOptional);
        flightRepository.save(flight);
    }

    @Transactional
    @Override
    public void delete(String flightId) throws ValidatorException {
        flightValidator.validId(flightId);
        Optional<Flight> flightOptional = flightRepository.findById(flightId);
        flightValidator.isDataActive(flightOptional);
        Flight flight = flightOptional.get();
        flight.setPassive(Boolean.TRUE);
        flightRepository.save(flight);
    }
}
