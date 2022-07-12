package com.senerk.casestudy.flightplanning.repository.flight;

import com.senerk.casestudy.flightplanning.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String>, FlightRepositoryCustom {
}
