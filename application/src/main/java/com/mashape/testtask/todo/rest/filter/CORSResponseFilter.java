package com.mashape.testtask.todo.rest.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

/**
 * For our API we have to enable cross-site HTTP requests. So it means that we have to
 * send Access-Control-Allow-Origin header back from server.
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public class CORSResponseFilter implements ContainerResponseFilter {

    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        /**
         * We should use Access-Control-Allow-Methods to limit types of requests from different domains.
         */
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
    }

}
