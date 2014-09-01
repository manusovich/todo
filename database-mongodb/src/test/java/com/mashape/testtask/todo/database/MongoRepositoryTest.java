package com.mashape.testtask.todo.database;


import com.mashape.testtask.todo.api.model.TodoEntity;
import com.mashape.testtask.todo.api.repository.TodoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Simple Test for MongoDB repository implementation
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/mashape/testtask/todo/applicationContext-todo-mangodb.xml"})
public class MongoRepositoryTest {
    private static final String PHONE = "123456";

    @Autowired
    private TodoRepository repository;

    @Test
    public void testInsertAndRemove() {
        TodoEntity todo = new TodoEntity();
        todo.setTitle("TestTitle1");
        todo.setBody("TestBody1");
        todo.setPhone(PHONE);
        repository.getTodoDao().save(todo);
        Assert.assertNotNull(todo.getId());

        /**
         * Remove all records for test phone
         */
        repository.getTodoDao().removeAll(PHONE);

        /**
         * Get records for test phone
         */
        List<TodoEntity> todoEntityList = repository.getTodoDao().readAll(PHONE);
        Assert.assertTrue(todoEntityList == null || todoEntityList.size() == 0);
    }
}
