package com.mashape.testtask.todo.api.exception;

/**
 * This exception can be used by Notification provider
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public class NotificationException extends TodoApplicationException {
    public NotificationException(String message) {
        super(ApplicationExceptionCode.NOTIFICATION, message);
    }
}
