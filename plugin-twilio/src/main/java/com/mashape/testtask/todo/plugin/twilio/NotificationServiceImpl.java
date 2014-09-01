package com.mashape.testtask.todo.plugin.twilio;


import com.mashape.testtask.todo.api.exception.NotificationException;
import com.mashape.testtask.todo.api.service.NotificationService;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Notification Provider Implementation: SMS Notification service through Twilio
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public class NotificationServiceImpl implements NotificationService {
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_ID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String TWILIO_SENDER = System.getenv("TWILIO_SENDER");

    @Override
    public void notifyAccountHolder(String phone, String messageBody) throws NotificationException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        // Build a filter for the MessageList
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Body", messageBody));
        params.add(new BasicNameValuePair("To", phone));
        params.add(new BasicNameValuePair("From", TWILIO_SENDER));

        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        try {
            messageFactory.create(params);
        } catch (TwilioRestException e) {
            throw new NotificationException(e.getMessage());
        }
    }
}
