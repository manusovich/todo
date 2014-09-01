package com.mashape.testtask.todo.rest.exception;

import com.mashape.testtask.todo.rest.model.ApplicationError;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception mapper for app errors which can be thrown from application
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    public Response toResponse(Throwable ex) {
        ApplicationError errorMessage = new ApplicationError();
        setHttpStatus(ex, errorMessage);
        errorMessage.setMessage(ex.getMessage());

        StringWriter errorStackTrace = new StringWriter();
        ex.printStackTrace(new PrintWriter(errorStackTrace));

        return Response.status(errorMessage.getStatus())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private void setHttpStatus(Throwable ex, ApplicationError errorMessage) {
        if (ex instanceof WebApplicationException) {
            errorMessage.setStatus(((WebApplicationException) ex).getResponse().getStatus());
        } else {
            errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }
}

