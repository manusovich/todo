package com.mashape.testtask.todo.rest.exception;

import com.mashape.testtask.todo.api.exception.TodoApplicationException;
import com.mashape.testtask.todo.rest.model.ApplicationError;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Exception mapper for application errors.
 * Creates wrapper for exception and returns back to client
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public class ApplicationExceptionMapper implements ExceptionMapper<TodoApplicationException> {

    public Response toResponse(TodoApplicationException ex) {
        return Response.status(ex.getStatus())
                .entity(new ApplicationError(ex))
                .type(MediaType.APPLICATION_JSON).
                        build();
    }

}
