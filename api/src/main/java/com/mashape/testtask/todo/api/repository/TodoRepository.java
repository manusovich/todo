package com.mashape.testtask.todo.api.repository;

import com.mashape.testtask.todo.api.model.TodoEntity;

/**
 * Todо application repository contract. Implementation has to manage all
 * DAO implementations which are available
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public interface TodoRepository {
    Dao<TodoEntity> getTodoDao();
}
