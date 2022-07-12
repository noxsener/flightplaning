package com.senerk.casestudy.flightplanning.service.validators.data;

import com.senerk.casestudy.flightplanning.entity.EntityClass;
import com.senerk.casestudy.flightplanning.models.ValidatorException;

import java.util.Optional;

public interface CommonValidator {
    void validId(String id) throws ValidatorException;

    void validId(EntityClass entityClass) throws ValidatorException;

    void isDataActive(Optional<?> entityClassOptional) throws ValidatorException;
}
