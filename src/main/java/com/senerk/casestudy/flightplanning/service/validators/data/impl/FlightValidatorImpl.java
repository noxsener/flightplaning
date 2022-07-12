package com.senerk.casestudy.flightplanning.service.validators.data.impl;

import com.senerk.casestudy.flightplanning.entity.Flight;
import com.senerk.casestudy.flightplanning.models.ValidatorException;
import com.senerk.casestudy.flightplanning.repository.airport.AirPortRepository;
import com.senerk.casestudy.flightplanning.repository.flight.FlightRepository;
import com.senerk.casestudy.flightplanning.service.validators.data.FlightValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.NoResultException;
import java.util.Optional;

@Service("flightValidator")
public class FlightValidatorImpl extends CommonValidatorImpl implements FlightValidator {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final FlightRepository flightRepository;
    private final AirPortRepository airPortRepository;

    private static final String FLIGHT_CODE_IS_ALREADY_TAKEN = "This flight code is already taken";
    private static final String MAX_AIRPORT_TRAFFIC_EXCEEDED = "Max airport traffic is exceeded";
    private static final String THERE_IS_AN_ERROR_ON_GETTING_FLIGHT_COUNT_FOR_AIRPORT = "An error occured on getting flight count for airport ";

    @Value("${restrictions.airport.flightcount.max: 3}")
    private Integer airPortMaxFlightCount;

    public FlightValidatorImpl(FlightRepository flightRepository, AirPortRepository airPortRepository) {
        this.flightRepository = flightRepository;
        this.airPortRepository = airPortRepository;
    }

    @Override
    public void canFlightPersist(Flight flight) throws ValidatorException {
        Long flightCountInSameDayAndSameTrip = flightRepository.getFlightCountBySourceAirportTargetAirPortAndDate(flight.getSourceAirPort(), flight.getArrivalAirPort(), flight.getTakeOffDate().toLocalDate());

        // Can we get our parameter?
        if (ObjectUtils.isEmpty(flightCountInSameDayAndSameTrip)) {
            LOG.warn(THERE_IS_AN_ERROR_ON_GETTING_FLIGHT_COUNT_FOR_AIRPORT);
            throw new ValidatorException(THERE_IS_AN_ERROR_ON_GETTING_FLIGHT_COUNT_FOR_AIRPORT);
        }

        // Is limit capacity has been filled?
        if (flightCountInSameDayAndSameTrip >= airPortMaxFlightCount) {
            LOG.warn(MAX_AIRPORT_TRAFFIC_EXCEEDED);
            throw new ValidatorException(MAX_AIRPORT_TRAFFIC_EXCEEDED);
        }

        // Is airports are valid?
        if (StringUtils.isBlank(flight.getSourceAirPort().getId()) || StringUtils.isBlank(flight.getArrivalAirPort().getId())) {
            LOG.warn(VALIDATOR_DATA_ID_IS_NOT_VALID);
            throw new ValidatorException(VALIDATOR_DATA_ID_IS_NOT_VALID);
        } else {
            isDataActive(airPortRepository.findById(flight.getSourceAirPort().getId()));
            isDataActive(airPortRepository.findById(flight.getArrivalAirPort().getId()));
        }

        // Is there any same flight code in DB? (Self Excluded)
        try {
            Optional<Flight> flightOptional = flightRepository.getFlightByCode(flight.getFlightCode());
            if (flightOptional.isPresent() && StringUtils.isNotBlank(flight.getId()) && !flight.getId().equalsIgnoreCase(flightOptional.get().getId())) {
                throw new ValidatorException(FLIGHT_CODE_IS_ALREADY_TAKEN);
            }
        } catch (EmptyResultDataAccessException | NoResultException ignored) {
            // We can save to DB in peacefully.
        }
    }
}
