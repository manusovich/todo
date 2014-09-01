package com.mashape.testtask.todo.api.model;

import org.springframework.data.annotation.Id;

/**
 * Base entity mode for all entities. Describes identification field
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public abstract class BaseEntity {
    @Id
    private String id;

    public BaseEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
