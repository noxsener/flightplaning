package com.senerk.casestudy.flightplanning.converter;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.entity.Flight;
import com.senerk.casestudy.flightplanning.entitydto.AirPortDto;
import com.senerk.casestudy.flightplanning.entitydto.FlightDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    AirPortDto map(AirPort value);

    AirPort map(AirPortDto value);

    FlightDto map(Flight value);

    Flight map(FlightDto value);
}
