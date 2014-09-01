package com.mashape.testtask.todo.rest.model;

import com.mashape.testtask.todo.api.exception.ApplicationExceptionCode;
import com.mashape.testtask.todo.api.exception.TodoApplicationException;
import org.springframework.beans.BeanUtils;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean for application error which will be returned to the client
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
@XmlRootElement
public class ApplicationError {

    /**
     * contains the same HTTP Status code returned by the server
     */
    @XmlElement(name = "status")
    int status;

    /**
     * application specific error code
     */
    @XmlElement(name = "code")
    ApplicationExceptionCode code;

    /**
     * message describing the error
     */
    @XmlElement(name = "message")
    String message;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ApplicationExceptionCode getCode() {
        return code;
    }

    public void setCode(ApplicationExceptionCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ApplicationError(TodoApplicationException ex) {
        BeanUtils.copyProperties(ex, this);
    }

    public ApplicationError(NotFoundException ex) {
        this.status = Response.Status.NOT_FOUND.getStatusCode();
        this.message = ex.getMessage();
    }

    public ApplicationError() {
    }
}
