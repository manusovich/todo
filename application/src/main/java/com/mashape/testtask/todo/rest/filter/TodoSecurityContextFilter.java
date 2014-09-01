package com.mashape.testtask.todo.rest.filter;

import com.mashape.testtask.todo.api.model.UserRoles;
import com.mashape.testtask.todo.rest.TodoRestApplication;
import org.springframework.util.StringUtils;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

/**
 * We should set custom security context because we have custom authorization
 * logic based on cookie checking
 * <p/>
 * Created by Alex Manusovich on 8/31/14.
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class TodoSecurityContextFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        /**
         * Set custom security context
         */
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return new Principal() {
                    @Override
                    public String getName() {
                        return "";
                    }
                };
            }

            @Override
            public boolean isUserInRole(String string) {
                /**
                 * If API needs authorization user, we have to check cookie with session
                 */
                if (string != null && ("" + UserRoles.AUTHORIZED).equals(string)) {
                    return isAuthenticated(requestContext);
                }
                /**
                 * For all other roles, we allow execution
                 */
                return true;
            }

            @Override
            public boolean isSecure() {
                return requestContext.getSecurityContext().isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return requestContext.getSecurityContext().getAuthenticationScheme();
            }
        });
    }

    private boolean isAuthenticated(final ContainerRequestContext requestContext) {
        Map<String, Cookie> cookies = requestContext.getCookies();
        return cookies != null && cookies.get(TodoRestApplication.COOKIE_SESSION_PHONE) != null &&
                !StringUtils.isEmpty(cookies.get(TodoRestApplication.COOKIE_SESSION_PHONE).getValue());
    }
}

