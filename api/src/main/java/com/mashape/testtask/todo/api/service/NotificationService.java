package com.mashape.testtask.todo.api.service;

import com.mashape.testtask.todo.api.exception.NotificationException;

/**
 * Contract for notification service. Notification provider has to have ability notify user
 * with specific phone.
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public interface NotificationService {
    void notifyAccountHolder(String phone, String message) throws NotificationException;
}
