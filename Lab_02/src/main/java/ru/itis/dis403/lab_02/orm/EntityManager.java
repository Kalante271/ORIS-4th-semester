package ru.itis.dis403.lab_02.orm;

import java.util.List;

public interface EntityManager {
    <T> T save(T entity); // insert, update
    void remove(Object entity); // delete ... where id =
    <T> T find(Class<T> entityType, Object key); // select ... where id =
    <T> List<T> findAll(Class<T> entityType); // select ...
}
