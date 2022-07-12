package com.senerk.casestudy.flightplanning.converter;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.entity.Flight;
import com.senerk.casestudy.flightplanning.entitydto.AirPortDto;
import com.senerk.casestudy.flightplanning.entitydto.FlightDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-12T04:30:27+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_281 (Oracle Corporation)"
)
public class EntityMapperImpl implements EntityMapper {

    @Override
    public AirPortDto map(AirPort value) {
        if ( value == null ) {
            return null;
        }

        AirPortDto airPortDto = new AirPortDto();

        if ( value.getDisplayName() != null ) {
            airPortDto.setDisplayName( value.getDisplayName() );
        }
        if ( value.getCode() != null ) {
            airPortDto.setCode( value.getCode() );
        }
        if ( value.getId() != null ) {
            airPortDto.setId( value.getId() );
        }
        if ( value.getPassive() != null ) {
            airPortDto.setPassive( String.valueOf( value.getPassive() ) );
        }

        return airPortDto;
    }

    @Override
    public AirPort map(AirPortDto value) {
        if ( value == null ) {
            return null;
        }

        AirPort airPort = new AirPort();

        if ( value.getId() != null ) {
            airPort.setId( value.getId() );
        }
        if ( value.getPassive() != null ) {
            airPort.setPassive( Boolean.parseBoolean( value.getPassive() ) );
        }
        if ( value.getDisplayName() != null ) {
            airPort.setDisplayName( value.getDisplayName() );
        }
        if ( value.getCode() != null ) {
            airPort.setCode( value.getCode() );
        }

        return airPort;
    }

    @Override
    public FlightDto map(Flight value) {
        if ( value == null ) {
            return null;
        }

        FlightDto flightDto = new FlightDto();

        if ( value.getId() != null ) {
            flightDto.setId( value.getId() );
        }
        if ( value.getDisplayText() != null ) {
            flightDto.setDisplayText( value.getDisplayText() );
        }
        if ( value.getFlightCode() != null ) {
            flightDto.setFlightCode( value.getFlightCode() );
        }
        if ( value.getStatus() != null ) {
            flightDto.setStatus( value.getStatus() );
        }
        if ( value.getTakeOffDate() != null ) {
            flightDto.setTakeOffDate( value.getTakeOffDate() );
        }
        if ( value.getPassive() != null ) {
            flightDto.setPassive( value.getPassive() );
        }
        if ( value.getSourceAirPort() != null ) {
            flightDto.setSourceAirPort( map( value.getSourceAirPort() ) );
        }
        if ( value.getArrivalAirPort() != null ) {
            flightDto.setArrivalAirPort( map( value.getArrivalAirPort() ) );
        }

        return flightDto;
    }

    @Override
    public Flight map(FlightDto value) {
        if ( value == null ) {
            return null;
        }

        Flight flight = new Flight();

        if ( value.getId() != null ) {
            flight.setId( value.getId() );
        }
        if ( value.getDisplayText() != null ) {
            flight.setDisplayText( value.getDisplayText() );
        }
        if ( value.getFlightCode() != null ) {
            flight.setFlightCode( value.getFlightCode() );
        }
        if ( value.getStatus() != null ) {
            flight.setStatus( value.getStatus() );
        }
        if ( value.getPassive() != null ) {
            flight.setPassive( value.getPassive() );
        }
        if ( value.getSourceAirPort() != null ) {
            flight.setSourceAirPort( map( value.getSourceAirPort() ) );
        }
        if ( value.getArrivalAirPort() != null ) {
            flight.setArrivalAirPort( map( value.getArrivalAirPort() ) );
        }
        if ( value.getTakeOffDate() != null ) {
            flight.setTakeOffDate( value.getTakeOffDate() );
        }

        return flight;
    }
}
