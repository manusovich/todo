package com.mashape.testtask.todo.rest;

import com.mashape.testtask.todo.rest.exception.ApplicationExceptionMapper;
import com.mashape.testtask.todo.rest.exception.GenericExceptionMapper;
import com.mashape.testtask.todo.rest.exception.NotFoundExceptionMapper;
import com.mashape.testtask.todo.rest.filter.CORSResponseFilter;
import com.mashape.testtask.todo.rest.filter.LoggingResponseFilter;
import com.mashape.testtask.todo.rest.resource.SessionResource;
import com.mashape.testtask.todo.rest.resource.TodoResource;
import com.mashape.testtask.todo.rest.filter.TodoSecurityContextFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.server.validation.ValidationFeature;

/**
 * Jersey Application Configuration
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public class TodoRestApplication extends ResourceConfig {
    /**
     * Cookie name for session token
     */
    public static final String COOKIE_SESSION_PHONE = "MASHAPE_TODO_SESSION_PHONE";

    /**
     * Register JAX-RS application components.
     */
    public TodoRestApplication() {
        // Application API
        register(SessionResource.class);
        register(TodoResource.class);

        // Filters
        register(RequestContextFilter.class);
        register(LoggingResponseFilter.class);
        register(CORSResponseFilter.class);

        // Exception mappers
        register(GenericExceptionMapper.class);
        register(ApplicationExceptionMapper.class);
        register(NotFoundExceptionMapper.class);

        // Features
        register(JacksonFeature.class);
        register(MultiPartFeature.class);
        register(EntityFilteringFeature.class);

        // Validation
        register(ValidationFeature.class);
        /**
         * By default jersey sends invalid data format for validation errors.
         * We prefer receive that as part of response
         */
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);


        // Security
        register(RolesAllowedDynamicFeature.class);
        register(TodoSecurityContextFilter.class);
    }
}
