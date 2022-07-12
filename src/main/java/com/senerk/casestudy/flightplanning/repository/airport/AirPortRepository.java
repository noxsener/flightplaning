package com.senerk.casestudy.flightplanning.repository.airport;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirPortRepository extends JpaRepository<AirPort, String>, AirPortRepositoryCustom {
}
