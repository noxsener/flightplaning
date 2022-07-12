package com.senerk.casestudy.flightplanning.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CommonRepositoryImpl<T> implements CommonRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> repositoryClass;

    public CommonRepositoryImpl(Class<T> repositoryClass) {
        this.repositoryClass = repositoryClass;
    }

    protected static final String PASSIVE = "passive";
    protected static final String CODE = "code";
    protected static final String FLIGHT_CODE = "flightCode";
    protected static final String DISPLAY_NAME = "displayName";
    protected static final String DISPLAY_TEXT = "displayText";

    @Override
    public T detach(T entity) {
        entityManager.detach(entity);
        return entity;
    }

    @Override
    public List<T> findAllOnlyActive() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(repositoryClass);
        Root<T> c = query.from(repositoryClass);
        Predicate predicate = cb.equal(c.get(PASSIVE), false);
        query.where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<T> findAllOnlyActive(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(repositoryClass);
        Root<T> c = query.from(repositoryClass);
        Predicate predicate = cb.equal(c.get(PASSIVE), false);
        query.where(predicate);
        if (pageable.getSort().isSorted()) {
            query.orderBy(QueryUtils.toOrders(pageable.getSort(), c, cb));
        }
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        if (pageable.isPaged()) {
            typedQuery.setFirstResult((int) pageable.getOffset());
            typedQuery.setMaxResults(pageable.getPageSize());
        }

        return typedQuery.getResultList();
    }


}
