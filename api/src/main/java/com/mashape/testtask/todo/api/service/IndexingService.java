package com.mashape.testtask.todo.api.service;

import com.mashape.testtask.todo.api.exception.IndexingException;
import com.mashape.testtask.todo.api.model.TodoEntity;

import java.util.List;

/**
 * Contract for indexing provider. Provider has to allow index data and make
 * search through indexed data
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public interface IndexingService {
    void index(TodoEntity todoEntity) throws IndexingException;

    void updateIndex(TodoEntity todoEntity) throws IndexingException;

    List<TodoEntity> search(String query) throws IndexingException;
}
