package com.mashape.testtask.todo.service;

import com.mashape.testtask.todo.api.exception.IndexingException;
import com.mashape.testtask.todo.api.exception.NotificationException;
import com.mashape.testtask.todo.api.model.TodoEntity;
import com.mashape.testtask.todo.api.repository.Dao;
import com.mashape.testtask.todo.api.repository.TodoRepository;
import com.mashape.testtask.todo.api.service.IndexingService;
import com.mashape.testtask.todo.api.service.NotificationService;
import com.mashape.testtask.todo.api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Todо Business Service Implementation
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
@Component
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository repository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private IndexingService indexingService;


    @Override
    public void createTodo(String phone, String title,
                           String body, boolean done) throws IndexingException {
        Dao<TodoEntity> dao = getRepository().getTodoDao();

        TodoEntity entity = new TodoEntity();
        entity.setPhone(phone);
        entity.setTitle(title);
        entity.setBody(body);
        entity.setDone(done);
        dao.save(entity);

        getIndexingService().index(entity);
    }

    @Override
    public List<TodoEntity> readAll(String phone) {
        return getRepository().getTodoDao().readAll(phone);
    }

    @Override
    public TodoEntity readById(String phone, String id) {
        return getRepository().getTodoDao().read(phone, id);
    }

    @Override
    public List<TodoEntity> search(String phone, String query) throws IndexingException {
        return getIndexingService().search(query);
    }

    @Override
    public void remove(String phone, String id) {
        getRepository().getTodoDao().remove(phone, id);
    }

    @Override
    public void removeAll(String phone) {
        getRepository().getTodoDao().removeAll(phone);
    }

    @Override
    public void update(TodoEntity entity) throws NotificationException, IndexingException {
        if (entity.getId() == null || StringUtils.isEmpty(entity.getId())) {
            throw new IllegalStateException("Entity doesn't have ID");
        }
        TodoEntity fromRepository = readById(entity.getPhone(), entity.getId());

        /**
         * We have to check if todо record has been marked as done. In this case we have to send
         * notification
         */
        if (!fromRepository.isDone() && entity.isDone()) {
            getNotificationService().notifyAccountHolder(entity.getPhone(),
                    String.format("'%s' task has been marked as done", entity.getTitle()));
        }

        getRepository().getTodoDao().update(entity);
        getIndexingService().updateIndex(entity);
    }

    public TodoRepository getRepository() {
        return repository;
    }

    public void setRepository(TodoRepository repository) {
        this.repository = repository;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public IndexingService getIndexingService() {
        return indexingService;
    }

    public void setIndexingService(IndexingService indexingService) {
        this.indexingService = indexingService;
    }
}
