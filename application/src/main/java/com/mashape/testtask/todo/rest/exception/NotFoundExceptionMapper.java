package com.mashape.testtask.todo.rest.exception;

import com.mashape.testtask.todo.rest.model.ApplicationError;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Exception mapper for 404 errors.
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException ex) {
        return Response.status(ex.getResponse().getStatus())
                .entity(new ApplicationError(ex))
                .type(MediaType.TEXT_HTML)
                .entity("Use /api/service or /api/todo rest services")
                .build();
    }

}
