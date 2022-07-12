package com.senerk.casestudy.flightplanning.service.validators.data.impl;

import com.senerk.casestudy.flightplanning.entity.EntityClass;
import com.senerk.casestudy.flightplanning.models.ValidatorException;
import com.senerk.casestudy.flightplanning.service.validators.data.CommonValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("commonValidator")
public class CommonValidatorImpl implements CommonValidator {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public static final String VALIDATOR_DATA_ID_IS_NOT_VALID = "Data id is not valid";
    public static final String VALIDATOR_DATA_IS_NOT_VALID = "Data is not valid";
    private static final String DATA_NOT_FOUND = "Data not found";

    @Override
    public void validId(String id) throws ValidatorException {
        if (StringUtils.isBlank(id) || id.length() != 36) {
            LOG.warn(VALIDATOR_DATA_ID_IS_NOT_VALID);
            throw new ValidatorException(VALIDATOR_DATA_ID_IS_NOT_VALID);
        }
    }

    @Override
    public void validId(final EntityClass entityClass) throws ValidatorException {
        if (entityClass == null) {
            LOG.warn(VALIDATOR_DATA_ID_IS_NOT_VALID);
            throw new ValidatorException(VALIDATOR_DATA_IS_NOT_VALID);
        }
        validId(entityClass.getId());
    }

    @Override
    public void isDataActive(Optional<?> entityClassOptional) throws ValidatorException {
        if (!entityClassOptional.isPresent() || !(entityClassOptional.get() instanceof EntityClass)) {
            LOG.warn(DATA_NOT_FOUND);
            throw new ValidatorException(DATA_NOT_FOUND);
        }
        EntityClass entityClass = (EntityClass) entityClassOptional.get();
        if (entityClass.getPassive()) {
            LOG.warn(DATA_NOT_FOUND);
            throw new ValidatorException(DATA_NOT_FOUND);
        }
        validId(entityClass);
    }
}
