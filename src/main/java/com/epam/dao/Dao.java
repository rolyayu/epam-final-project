package com.epam.dao;

import com.epam.entity.Entity;

public interface Dao<T extends Entity> {
    boolean create(T t);
    T read(Long id);
    boolean update(Long id);
    boolean delete(Long id);
}
