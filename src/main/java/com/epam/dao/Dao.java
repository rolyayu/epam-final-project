package com.epam.dao;

import com.epam.entity.Entity;

public interface Dao<T extends Entity> {
    boolean create(T t) throws DaoException;
    T read(Long id) throws DaoException;
    boolean update(T t) throws DaoException;
    boolean delete(Long id) throws DaoException;
}
