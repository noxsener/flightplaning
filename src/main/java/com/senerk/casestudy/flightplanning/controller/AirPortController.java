package com.senerk.casestudy.flightplanning.controller;


import com.senerk.casestudy.flightplanning.converter.EntityMapper;
import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.entitydto.AirPortDto;
import com.senerk.casestudy.flightplanning.models.ApiResponse;
import com.senerk.casestudy.flightplanning.models.ValidatorException;
import com.senerk.casestudy.flightplanning.service.airport.AirPortService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(path = "/api/rest/airport")
@RestController
public class AirPortController {

    private final AirPortService airPortService;

    public AirPortController(AirPortService airPortService) {
        this.airPortService = airPortService;
    }

    /**
     * Returns all AirPorts in database
     *
     * @return AirPort Data Collection
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AirPortDto>>> getAirPorts() {
        List<AirPort> airPortList = airPortService.getAirports();
        List<AirPortDto> airPortDtoList = airPortList.stream().map(EntityMapper.INSTANCE::map).collect(Collectors.toList());
        return ApiResponse.ok(airPortDtoList);
    }

    /**
     * Returns all AirPorts in database
     *
     * @param code Airport Code
     * @return AirPort Data
     */
    @GetMapping("code/{code}")
    public ResponseEntity<ApiResponse<AirPortDto>> getAirportByCode(@PathVariable String code) {
        try {
            final AirPort airPort = airPortService.getAirportByCode(code);
            final AirPortDto airPortDto = EntityMapper.INSTANCE.map(airPort);
            return ApiResponse.ok(airPortDto);
        } catch (EmptyResultDataAccessException | NoResultException nre) {
            return ApiResponse.dataNotFound();
        }
    }

    /**
     * Returns all AirPorts in database
     *
     * @param displayName Text containing the searched name
     * @return AirPort Data Collection
     */
    @GetMapping("displayName/{displayName}")
    public ResponseEntity<ApiResponse<List<AirPortDto>>> getAirportByNameContains(@PathVariable String displayName) {
        final List<AirPort> airPortList = airPortService.getAirportByNameContains(displayName);
        final List<AirPortDto> airPortDtoList = airPortList.stream().map(EntityMapper.INSTANCE::map).collect(Collectors.toList());
        return ApiResponse.ok(airPortDtoList);
    }

    /**
     * Returns all AirPorts in database with pagination
     *
     * @param pageable paging and sorting info
     * @return AirPort Data Collection
     */
    @GetMapping("pageable")
    public ResponseEntity<ApiResponse<List<AirPortDto>>> getAirportsByPaging(Pageable pageable) {
        try {
            final List<AirPort> airPortList = airPortService.getAirports(pageable);
            final List<AirPortDto> airPortDtoList = airPortList.stream().map(EntityMapper.INSTANCE::map).collect(Collectors.toList());
            return ApiResponse.ok(airPortDtoList);
        } catch (PropertyReferenceException pre) {
            return ApiResponse.badRequest(pre.getMessage());
        }
    }

    /**
     * Saves a new airport data
     *
     * @param airPortDto New Airport data
     * @return New Airport data id
     */
    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveAirPort(@RequestBody @Valid AirPortDto airPortDto) {
        final AirPort airPort = EntityMapper.INSTANCE.map(airPortDto);
        return ApiResponse.created(airPortService.save(airPort));
    }

    /**
     * Updates a new airport data
     *
     * @param airPortDto New Airport data
     * @return Nothing
     */
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateAirPort(@RequestBody @Valid AirPortDto airPortDto) {
        try {
            airPortService.update(EntityMapper.INSTANCE.map(airPortDto));
            return ApiResponse.ok();
        } catch (ValidatorException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * Deletes Airport data of given id
     *
     * @param airPortId Id of Airport Data
     * @return Nothing
     */
    @DeleteMapping("id/{airPortId}")
    public ResponseEntity<ApiResponse<Void>> deleteAirPort(@PathVariable String airPortId) {
        try {
            airPortService.delete(airPortId);
            return ApiResponse.ok();
        } catch (ValidatorException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
