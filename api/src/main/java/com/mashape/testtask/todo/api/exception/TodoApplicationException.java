package com.mashape.testtask.todo.api.exception;

/**
 * Generic exception for application.
 * Contains code & message which will be returned to user
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public class TodoApplicationException extends Exception {
    public static final int DEFAULT_HTTP_STATUS_CODE = 500;

    /**
     * Contains the same HTTP Status code returned by the server
     */
    private int status = DEFAULT_HTTP_STATUS_CODE;

    /**
     * Application specific error code
     */
    private ApplicationExceptionCode code = ApplicationExceptionCode.GENERIC;

    /**
     * message describing the error
     */
    private String message;

    public TodoApplicationException() {
        super();
    }

    public TodoApplicationException(String message) {
        super(message);
    }

    public TodoApplicationException(ApplicationExceptionCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public TodoApplicationException(int status, ApplicationExceptionCode code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.message = message;
    }

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
}
