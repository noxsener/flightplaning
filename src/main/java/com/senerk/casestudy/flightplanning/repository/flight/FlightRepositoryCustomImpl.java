package com.senerk.casestudy.flightplanning.repository.flight;

import com.senerk.casestudy.flightplanning.entity.AirPort;
import com.senerk.casestudy.flightplanning.entity.Flight;
import com.senerk.casestudy.flightplanning.repository.CommonRepositoryImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class FlightRepositoryCustomImpl extends CommonRepositoryImpl<Flight> implements FlightRepositoryCustom {

    private static final String SOURCE_AIRPORT = "sourceAirPort";
    private static final String ARRIVAL_AIRPORT = "arrivalAirPort";
    private static final String TAKE_OFF_DATE = "takeOffDate";

    public FlightRepositoryCustomImpl() {
        super(Flight.class);
    }

    @Override
    public Optional<Flight> getFlightByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return Optional.empty();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> query = cb.createQuery(Flight.class);
        Root<Flight> c = query.from(Flight.class);

        Predicate predicate = cb.equal(c.get(PASSIVE), false);
        predicate = cb.and(predicate, cb.equal(cb.lower(c.get(FLIGHT_CODE)), code.toLowerCase()));
        query.where(predicate);
        return Optional.of(entityManager.createQuery(query).getSingleResult());
    }

    @Override
    public List<Flight> getFlightByAirPortId(String airPortId, Pageable pageable) {
        if (StringUtils.isBlank(airPortId)) {
            return Collections.emptyList();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> query = cb.createQuery(Flight.class);
        Root<Flight> c = query.from(Flight.class);

        Predicate predicate = cb.equal(c.get(PASSIVE), false);
        AirPort searchedAirPort = new AirPort();
        searchedAirPort.setId(airPortId);
        predicate = cb.and(predicate, cb.or(cb.equal(c.get(SOURCE_AIRPORT), searchedAirPort), cb.equal(c.get(ARRIVAL_AIRPORT), searchedAirPort)));
        query.where(predicate);
        if (pageable.getSort().isSorted()) {
            query.orderBy(QueryUtils.toOrders(pageable.getSort(), c, cb));
        }
        TypedQuery<Flight> typedQuery = entityManager.createQuery(query);
        if (pageable.isPaged()) {
            typedQuery.setFirstResult((int) pageable.getOffset());
            typedQuery.setMaxResults(pageable.getPageSize());
        }
        return typedQuery.getResultList();
    }

    @Override
    public List<Flight> findByNameIsContains(String text, Pageable pageable) {
        if (StringUtils.isBlank(text)) {
            return Collections.emptyList();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> query = cb.createQuery(Flight.class);
        Root<Flight> c = query.from(Flight.class);

        Predicate predicate = cb.equal(c.get(PASSIVE), false);
        predicate = cb.and(predicate, cb.like(cb.lower(c.get(DISPLAY_TEXT)), "%" + text.toLowerCase() + "%"));
        query.where(predicate);
        if (pageable.getSort().isSorted()) {
            query.orderBy(QueryUtils.toOrders(pageable.getSort(), c, cb));
        }
        TypedQuery<Flight> typedQuery = entityManager.createQuery(query);
        if (pageable.isPaged()) {
            typedQuery.setFirstResult((int) pageable.getOffset());
            typedQuery.setMaxResults(pageable.getPageSize());
        }
        return typedQuery.getResultList();
    }

    @Override
    public List<Flight> getFlightsBySourceAirportTargetAirPortAndDate(AirPort sourceAirPort, AirPort targetAirPort, LocalDate date) {
        if (Objects.isNull(sourceAirPort) || Objects.isNull(targetAirPort) || Objects.isNull(date)) {
            return Collections.emptyList();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> query = cb.createQuery(Flight.class);
        Root<Flight> c = query.from(Flight.class);

        Predicate predicate = cb.equal(c.get(PASSIVE), false);

        Predicate subPredicate1 = cb.and(cb.equal(c.get(SOURCE_AIRPORT), sourceAirPort), cb.equal(c.get(ARRIVAL_AIRPORT), targetAirPort));
        Predicate subPredicate2 = cb.and(cb.equal(c.get(SOURCE_AIRPORT), targetAirPort), cb.equal(c.get(ARRIVAL_AIRPORT), sourceAirPort));
        predicate = cb.and(predicate, cb.or(subPredicate1, subPredicate2));

        predicate = cb.and(predicate, cb.between(c.get(TAKE_OFF_DATE), date.atStartOfDay(), date.atStartOfDay().plusDays(1).minusSeconds(1)));
        query.where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Long getFlightCountBySourceAirportTargetAirPortAndDate(AirPort sourceAirPort, AirPort targetAirPort, LocalDate date) {
        if (Objects.isNull(sourceAirPort) || Objects.isNull(targetAirPort) || Objects.isNull(date)) {
            return null;
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Flight> c = query.from(Flight.class);

        Predicate predicate = cb.equal(c.get(PASSIVE), false);

        Predicate subPredicate1 = cb.and(cb.equal(c.get(SOURCE_AIRPORT), sourceAirPort), cb.equal(c.get(ARRIVAL_AIRPORT), targetAirPort));
        Predicate subPredicate2 = cb.and(cb.equal(c.get(SOURCE_AIRPORT), targetAirPort), cb.equal(c.get(ARRIVAL_AIRPORT), sourceAirPort));
        predicate = cb.and(predicate, cb.or(subPredicate1, subPredicate2));

        predicate = cb.and(predicate, cb.between(c.get(TAKE_OFF_DATE), date.atStartOfDay(), date.atStartOfDay().plusDays(1).minusSeconds(1)));
        query.select(cb.count(c.get(FieldUtils.getFieldsListWithAnnotation(Flight.class, javax.persistence.Id.class).get(0).getName())));
        query.where(predicate);
        return entityManager.createQuery(query).getSingleResult();
    }
}
