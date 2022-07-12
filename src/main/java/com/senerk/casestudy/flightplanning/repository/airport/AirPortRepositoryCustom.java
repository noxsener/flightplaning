package com.senerk.casestudy.flightplanning.repository.airport;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.repository.CommonRepository;

import java.util.List;
import java.util.Optional;

public interface AirPortRepositoryCustom extends CommonRepository<AirPort> {
    Optional<AirPort> findByCode(String code);

    List<AirPort> findByNameIsContains(String name);
}
