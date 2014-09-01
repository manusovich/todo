package com.mashape.testtask.todo.rest.model;

import com.mashape.testtask.todo.api.model.TodoEntity;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Bean for Todо objects. This is just DTO file which will be used in rest service.
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Todo implements Serializable {
    @XmlElement(name = TodoEntity.ID)
    private String id;

    @XmlElement(name = TodoEntity.TITLE)
    @NotNull(message = "The field title has to be at least 5 characters")
    @Length(min = 5, message = "The field title has to be at least 5 characters")
    private String title;

    @XmlElement(name = TodoEntity.BODY)
    private String body;

    @XmlElement(name = TodoEntity.DONE)
    private boolean done;

    public Todo() {
    }

    public Todo(TodoEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public Todo(String title, String description, boolean done) {
        this.title = title;
        this.body = description;
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
