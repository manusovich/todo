package com.mashape.testtask.todo.database.mongodb;

import com.mashape.testtask.todo.api.model.TodoEntity;
import com.mashape.testtask.todo.api.repository.Dao;
import com.mashape.testtask.todo.api.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * MongoDB Repository Implementation
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
@Component
public class MongoDbRepository implements TodoRepository {
    @Autowired
    @Qualifier("todo") // we have to use qualifier for future
    private Dao<TodoEntity> todoDao;

    public Dao<TodoEntity> getTodoDao() {
        return todoDao;
    }

    public void setTodoDao(Dao<TodoEntity> todoDao) {
        this.todoDao = todoDao;
    }
}
