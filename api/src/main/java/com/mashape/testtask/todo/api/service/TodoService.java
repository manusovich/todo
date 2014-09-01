package com.mashape.testtask.todo.api.service;

import com.mashape.testtask.todo.api.exception.IndexingException;
import com.mashape.testtask.todo.api.exception.NotificationException;
import com.mashape.testtask.todo.api.model.TodoEntity;

import java.util.List;

/**
 * Contract for main service which has to connect all provides and implements main business logic
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public interface TodoService {
    /**
     * Create new todо object
     *
     * @param phone User's phone. This phone will be used for notifications
     * @param title Title for Todо object
     * @param body  Body for Todо object
     * @param done  This flag specifies if this todо is done or not
     * @throws IndexingException
     */
    void createTodo(String phone, String title,
                    String body, boolean done) throws IndexingException;

    /**
     * Read all todos by phone number
     *
     * @return List of records
     */
    List<TodoEntity> readAll(String phone);

    /**
     * Read specific record by Id. We have to pass phone for security reasons as well
     *
     * @return Record
     */
    TodoEntity readById(String phone, String id);

    /**
     * Update record
     *
     * @throws NotificationException During update process we can notify user about changing state of todо.
     *                               If we will meet any problems with notification, this exception can be throw
     */
    void update(TodoEntity entity) throws NotificationException, IndexingException;

    /**
     * Search records by query
     *
     * @return Records
     * @throws IndexingException This exception can be throw by Indexing provider
     */
    List<TodoEntity> search(String phone, String query) throws IndexingException;

    /**
     * Remove record by phone & id
     */
    void remove(String phone, String id);

    /**
     * Remove all records by phone
     */
    void removeAll(String phone);
}
