package com.mashape.testtask.todo.api.repository;

import java.util.List;

/**
 * Contact for all data access objects. Can be implemented by different provides
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public interface Dao<T> {

    String save(T todoEntity);

    String update(T todoEntity);

    T read(String phone, String id);

    List<T> readAll(String phone);

    void remove(String phone, String id);

    void removeAll(String phone);
}
