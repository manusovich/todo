package com.mashape.testtask.todo.database.mongodb.dao;

import com.mashape.testtask.todo.api.model.TodoEntity;
import com.mashape.testtask.todo.api.repository.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * MongoDB Todо DAO Implementation
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
@Component("todo")
public class MongoDbTodoDao implements Dao<TodoEntity> {
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public String save(TodoEntity todoEntity) {
        if (!StringUtils.isEmpty(todoEntity.getId())) {
            throw new IllegalStateException(
                    "Todo object has id, use update operation instead save");
        }
        mongoOperations.save(todoEntity);
        return todoEntity.getId();
    }

    @Override
    public String update(TodoEntity todoEntity) {
        if (StringUtils.isEmpty(todoEntity.getId())) {
            throw new IllegalStateException(
                    "Todo object has empty id, use create operation instead of update");
        }
        mongoOperations.save(todoEntity);
        return todoEntity.getId();
    }

    @Override
    public TodoEntity read(String phone, String id) {
        Query query = new Query(
                Criteria.where("id").is(id).and("phone").is(phone));
        return mongoOperations.findOne(query, TodoEntity.class);
    }

    @Override
    public List<TodoEntity> readAll(String phone) {
        Query query = new Query(Criteria.where("phone").is(phone));
        return mongoOperations.find(query, TodoEntity.class);
    }

    @Override
    public void remove(String phone, String id) {
        Query query = new Query(
                Criteria.where("id").is(id).and("phone").is(phone));
        mongoOperations.remove(query, TodoEntity.class);
    }

    @Override
    public void removeAll(String phone) {
        Query query = new Query(Criteria.where("phone").is(phone));
        mongoOperations.remove(query, TodoEntity.class);
    }

    public MongoOperations getMongoOperations() {
        return mongoOperations;
    }

    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
}
