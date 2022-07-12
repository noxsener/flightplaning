package com.senerk.casestudy.flightplanning.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommonRepository<T> {
    T detach(T entity);

    List<T> findAllOnlyActive();

    List<T> findAllOnlyActive(Pageable pageable);
}
