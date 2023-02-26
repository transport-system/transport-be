package com.transport.transport.service;

import java.util.List;

public interface CRUDService<T> {
    List<T> findAll();

    T findById(Long id);

    void save(T entity);

    void delete(Long id);

    void update(T entity);
}
