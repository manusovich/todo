package com.mashape.testtask.todo.api.model;

/**
 * Tоdo model class.
 * <p/>
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public class TodoEntity extends BaseEntity {
    public static final String ID = "id", TITLE = "title",
            BODY = "body", DONE = "done";

    /**
     * We have to store phone in each todо because we don't have special entity
     * for session or account
     */
    private String phone;

    private String title;
    private String body;
    private boolean done;

    public TodoEntity() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoEntity)) return false;
        if (!super.equals(o)) return false;

        TodoEntity todoEntity = (TodoEntity) o;

        if (done != todoEntity.done) return false;
        if (body != null ? !body.equals(todoEntity.body) : todoEntity.body != null) return false;
        if (!title.equals(todoEntity.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (done ? 1 : 0);
        return result;
    }
}
