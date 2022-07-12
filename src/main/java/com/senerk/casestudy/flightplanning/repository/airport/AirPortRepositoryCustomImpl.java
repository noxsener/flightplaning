package com.senerk.casestudy.flightplanning.repository.airport;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.repository.CommonRepositoryImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class AirPortRepositoryCustomImpl extends CommonRepositoryImpl<AirPort> implements AirPortRepositoryCustom {

    public AirPortRepositoryCustomImpl() {
        super(AirPort.class);
    }

    @Override
    public Optional<AirPort> findByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return Optional.empty();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AirPort> query = cb.createQuery(AirPort.class);
        Root<AirPort> c = query.from(AirPort.class);

        Predicate predicate = cb.equal(c.get(PASSIVE), false);
        predicate = cb.and(predicate, cb.equal(c.get(CODE), code));
        query.where(predicate);
        AirPort entity = entityManager.createQuery(query).getSingleResult();
        return Optional.of(entity);
    }

    @Override
    public List<AirPort> findByNameIsContains(String name) {
        if (StringUtils.isBlank(name)) {
            return Collections.emptyList();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AirPort> query = cb.createQuery(AirPort.class);
        Root<AirPort> c = query.from(AirPort.class);

        Predicate predicate = cb.equal(c.get(PASSIVE), false);
        predicate = cb.and(predicate, cb.like(cb.lower(c.get(DISPLAY_NAME)), "%" + name.toLowerCase() + "%"));
        query.where(predicate);
        return entityManager.createQuery(query).getResultList();
    }
}
