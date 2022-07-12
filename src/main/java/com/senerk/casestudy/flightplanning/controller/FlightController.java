package com.senerk.casestudy.flightplanning.controller;


import com.senerk.casestudy.flightplanning.converter.EntityMapper;
import com.senerk.casestudy.flightplanning.entity.Flight;
import com.senerk.casestudy.flightplanning.entitydto.FlightDto;
import com.senerk.casestudy.flightplanning.models.ApiResponse;
import com.senerk.casestudy.flightplanning.models.ValidatorException;
import com.senerk.casestudy.flightplanning.service.flight.FlightService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(path = "/api/rest/flight")
@RestController
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    /**
     * Return flight info
     * @param code flight's code
     * @return Flight info
     */
    @GetMapping("code/{code}")
    public ResponseEntity<ApiResponse<FlightDto>> getFlightByCode(@PathVariable String code) {
        try {
            Flight flight = flightService.getFlightByCode(code);
            FlightDto flightDto = EntityMapper.INSTANCE.map(flight);
            return ApiResponse.ok(flightDto);
        } catch (EmptyResultDataAccessException | NoResultException nre) {
            return ApiResponse.dataNotFound();
        }
    }

    /**
     * Return flights collection by name with pagination
     * @param name Flight name contains
     * @param pageable Paging info
     * @return Flights collection
     */
    @GetMapping("display-text/{name}/pageable")
    public ResponseEntity<ApiResponse<List<FlightDto>>> getFlightByNameContains(@PathVariable String name, Pageable pageable) {
        try {
            List<Flight> flightList = flightService.getFlightByNameContains(name, pageable);
            List<FlightDto> flightDtoList = flightList.stream().map(EntityMapper.INSTANCE::map).collect(Collectors.toList());
            return ApiResponse.ok(flightDtoList);
        } catch (PropertyReferenceException pre) {
            return ApiResponse.badRequest(pre.getMessage());
        }
    }

    /**
     * Flight list of airport with pagination
     * @param airportId Airport Id
     * @param pageable Paging info
     * @return Flights Collection
     */
    @GetMapping("airport-id/{airportId}/pageable")
    public ResponseEntity<ApiResponse<List<FlightDto>>> getFlightByAirPort(@PathVariable String airportId, Pageable pageable) {
        try {
            List<Flight> flightList = flightService.getFlightByAirPortId(airportId, pageable);
            List<FlightDto> flightDtoList = flightList.stream().map(EntityMapper.INSTANCE::map).collect(Collectors.toList());
            return ApiResponse.ok(flightDtoList);
        } catch (PropertyReferenceException | ValidatorException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * Save a new flight record
     * @param flightDto New Data Flight info for save
     * @return Saved data id
     */
    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveFlight(@RequestBody @Valid FlightDto flightDto) {
        try {
            Flight flight = EntityMapper.INSTANCE.map(flightDto);
            return ApiResponse.created(flightService.save(flight));
        } catch (ValidatorException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * Update a flight info that is already in db
     * @param flightDto New Data Flight info for update
     * @return Nothing
     */
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateFlight(@RequestBody @Valid FlightDto flightDto) {
        try {
            flightService.update(EntityMapper.INSTANCE.map(flightDto));
            return ApiResponse.ok();
        } catch (ValidatorException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * Delete a flight from db (set passive)
     * @param flightId flight id
     * @return nothing
     */
    @DeleteMapping("id/{flightId}")
    public ResponseEntity<ApiResponse<Void>> udeleteFlight(@PathVariable String flightId) {
        try {
            flightService.delete(flightId);
            return ApiResponse.ok();
        } catch (ValidatorException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
