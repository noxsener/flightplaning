package com.senerk.casestudy.flightplanning.service.airport.impl;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.models.ValidatorException;
import com.senerk.casestudy.flightplanning.repository.airport.AirPortRepository;
import com.senerk.casestudy.flightplanning.service.airport.AirPortService;
import com.senerk.casestudy.flightplanning.service.validators.data.CommonValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirPortServiceImpl implements AirPortService {

    private final AirPortRepository airPortRepository;
    private final CommonValidator commonValidator;

    public AirPortServiceImpl(AirPortRepository airPortRepository, CommonValidator commonValidator) {
        this.airPortRepository = airPortRepository;
        this.commonValidator = commonValidator;
    }

    @Override
    public List<AirPort> getAirports() {
        return airPortRepository.findAllOnlyActive();
    }

    @Override
    public List<AirPort> getAirports(Pageable pageable) {
        return airPortRepository.findAllOnlyActive(pageable);
    }

    @Override
    public AirPort getAirportByCode(final String code) {
        return airPortRepository.findByCode(code).orElse(null);
    }

    @Override
    public List<AirPort> getAirportByNameContains(final String displayName) {
        return airPortRepository.findByNameIsContains(displayName);
    }

    @Override
    public String save(AirPort airPort) {
        AirPort airPortSaved = airPortRepository.save(airPort);
        airPortRepository.detach(airPortSaved);
        return airPortSaved.getId();
    }

    @Override
    public void update(AirPort airPort) throws ValidatorException {
        commonValidator.validId(airPort);
        Optional<AirPort> airPortOptional = airPortRepository.findById(airPort.getId());
        commonValidator.isDataActive(airPortOptional);
        airPortRepository.detach(airPortRepository.save(airPort));
    }

    @Override
    public void delete(String airPortId) throws ValidatorException {
        commonValidator.validId(airPortId);
        Optional<AirPort> airPortOptional = airPortRepository.findById(airPortId);
        commonValidator.isDataActive(airPortOptional);
        AirPort airPort = airPortOptional.get();
        airPort.setPassive(Boolean.TRUE);
        airPortRepository.detach(airPortRepository.saveAndFlush(airPort));
    }
}
